package com.example.restaurantbooking.controller;

import com.example.restaurantbooking.model.Restaurant;
import com.example.restaurantbooking.model.Slot;
import com.example.restaurantbooking.repository.RestaurantRepository;
import com.example.restaurantbooking.repository.SlotRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/slots")
public class SlotController {

    private final SlotRepository slotRepository;
    private final RestaurantRepository restaurantRepository;

    public SlotController(SlotRepository slotRepository, RestaurantRepository restaurantRepository) {
        this.slotRepository = slotRepository;
        this.restaurantRepository = restaurantRepository;
    }

    // Create a slot for a restaurant
    @PostMapping("/restaurant/{restaurantId}")
    public ResponseEntity<Slot> createSlot(@PathVariable("restaurantId") Long restaurantId, @RequestBody Slot slot) {
        return restaurantRepository.findById(restaurantId)
                .map(restaurant -> {
                    slot.setRestaurant(restaurant);
                    Slot savedSlot = slotRepository.save(slot);
                    return ResponseEntity.ok(savedSlot);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Get all slots for a restaurant
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Slot>> getSlotsByRestaurant(@PathVariable("restaurantId") Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .map(restaurant -> ResponseEntity.ok(slotRepository.findByRestaurant(restaurant)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Get slot by ID
    @GetMapping("/{id}")
    public ResponseEntity<Slot> getSlotById(@PathVariable Long id) {
        return slotRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
