#!/bin/bash

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Function to create Maven wrapper in a directory
create_mvnw() {
    local dir=$1
    echo -e "${YELLOW}Creating Maven wrapper in $dir...${NC}"
    cd $dir || { echo -e "${RED}Failed to navigate to $dir${NC}"; return 1; }
    mvn -N wrapper:wrapper || { echo -e "${RED}Failed to create Maven wrapper in $dir${NC}"; return 1; }
    chmod +x mvnw
    echo -e "${GREEN}Maven wrapper created in $dir${NC}"
    cd ..
}

# Create Maven wrappers for all services
echo -e "${YELLOW}Creating Maven wrappers for all services...${NC}"

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo -e "${RED}Maven is not installed. Please install Maven first.${NC}"
    exit 1
fi

# Create wrappers for each service
create_mvnw "eureka-server"
create_mvnw "auth-service"
create_mvnw "inventory-service"
create_mvnw "product-service"
create_mvnw "api-gateway"

echo -e "${GREEN}All Maven wrappers have been created successfully!${NC}" 