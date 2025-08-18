package com.example.restaurantbooking.repository;

import com.example.restaurantbooking.model.Restaurant;
import com.example.restaurantbooking.model.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SlotRepository extends JpaRepository<Slot, Long> {
    List<Slot> findByRestaurant(Restaurant restaurant);
}
