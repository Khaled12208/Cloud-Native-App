package com.example.inventoryservice.service;

import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.model.Reservation;
import com.example.inventoryservice.repository.InventoryRepository;
import com.example.inventoryservice.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ReservationRepository reservationRepository;

    public List<Inventory> getAllItems() {
        return inventoryRepository.findAll();
    }

    public Optional<Inventory> getItemById(Long id) {
        return inventoryRepository.findById(id);
    }

    public Inventory createItem(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public Optional<Inventory> updateItem(Long id, Inventory inventory) {
        return inventoryRepository.findById(id)
                .map(existingItem -> {
                    existingItem.setName(inventory.getName());
                    existingItem.setDescription(inventory.getDescription());
                    existingItem.setQuantity(inventory.getQuantity());
                    existingItem.setPrice(inventory.getPrice());
                    return inventoryRepository.save(existingItem);
                });
    }

    public void deleteItem(Long id) {
        inventoryRepository.deleteById(id);
    }

    public Integer getAvailableStock(Long inventoryId) {
        Optional<Inventory> inventory = inventoryRepository.findById(inventoryId);
        if (inventory.isEmpty()) {
            return 0;
        }

        Integer totalQuantity = inventory.get().getQuantity();
        Integer reservedQuantity = reservationRepository.getReservedQuantity(inventoryId);
        
        return totalQuantity - (reservedQuantity != null ? reservedQuantity : 0);
    }

    @Transactional
    public Optional<Reservation> createReservation(Long inventoryId, Integer quantity, String orderId) {
        Optional<Inventory> inventory = inventoryRepository.findById(inventoryId);
        if (inventory.isEmpty()) {
            return Optional.empty();
        }

        Integer availableStock = getAvailableStock(inventoryId);
        if (availableStock < quantity) {
            return Optional.empty();
        }

        Reservation reservation = new Reservation();
        reservation.setInventory(inventory.get());
        reservation.setQuantity(quantity);
        reservation.setOrderId(orderId);
        reservation.setCreatedAt(LocalDateTime.now());
        reservation.setExpiresAt(LocalDateTime.now().plusMinutes(30)); // 30 minutes expiry
        reservation.setStatus(Reservation.ReservationStatus.PENDING);

        return Optional.of(reservationRepository.save(reservation));
    }

    @Transactional
    public Optional<Reservation> confirmReservation(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .map(reservation -> {
                    if (reservation.getStatus() == Reservation.ReservationStatus.PENDING) {
                        reservation.setStatus(Reservation.ReservationStatus.CONFIRMED);
                        return reservationRepository.save(reservation);
                    }
                    return reservation;
                });
    }

    @Transactional
    public Optional<Reservation> cancelReservation(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .map(reservation -> {
                    if (reservation.getStatus() == Reservation.ReservationStatus.PENDING) {
                        reservation.setStatus(Reservation.ReservationStatus.CANCELLED);
                        return reservationRepository.save(reservation);
                    }
                    return reservation;
                });
    }

    public List<Reservation> getReservationsByOrderId(String orderId) {
        return reservationRepository.findByOrderId(orderId);
    }

    @Transactional
    public void cleanupExpiredReservations() {
        List<Reservation> expiredReservations = reservationRepository.findExpiredReservations(LocalDateTime.now());
        expiredReservations.forEach(reservation -> {
            reservation.setStatus(Reservation.ReservationStatus.EXPIRED);
            reservationRepository.save(reservation);
        });
    }
} 