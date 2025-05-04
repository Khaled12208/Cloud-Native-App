package com.example.inventoryservice.repository;

import com.example.inventoryservice.model.Reservation;
import com.example.inventoryservice.model.Reservation.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByInventoryId(Long inventoryId);
    
    List<Reservation> findByOrderId(String orderId);
    
    @Query("SELECT r FROM Reservation r WHERE r.status = :#{T(com.example.inventoryservice.model.Reservation.ReservationStatus).PENDING} AND r.expiresAt < :now")
    List<Reservation> findExpiredReservations(@Param("now") LocalDateTime now);
    
    @Query("SELECT SUM(r.quantity) FROM Reservation r WHERE r.inventory.id = :inventoryId AND r.status = :#{T(com.example.inventoryservice.model.Reservation.ReservationStatus).CONFIRMED}")
    Integer getReservedQuantity(@Param("inventoryId") Long inventoryId);
} 