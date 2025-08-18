package com.example.restaurantbooking.repository;

import com.example.restaurantbooking.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByRestaurantIdAndDate(Long restaurantId, LocalDate date);
}
