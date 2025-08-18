package com.example.restaurantbooking.controller;

import com.example.restaurantbooking.model.Inventory;
import com.example.restaurantbooking.repository.InventoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventories")
public class InventoryController {

    private final InventoryRepository inventoryRepository;

    public InventoryController(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    // Create inventory
    @PostMapping
    public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory) {
        return ResponseEntity.ok(inventoryRepository.save(inventory));
    }

    // Get all inventories
    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventories() {
        return ResponseEntity.ok(inventoryRepository.findAll());
    }

    // Get inventory for a restaurant on a date
    @GetMapping("/restaurant/{restaurantId}/date/{date}")
    public ResponseEntity<List<Inventory>> getInventoriesByRestaurantAndDate(
            @PathVariable Long restaurantId,
            @PathVariable String date) {
        return ResponseEntity.ok(inventoryRepository.findByRestaurantIdAndDate(restaurantId, java.time.LocalDate.parse(date)));
    }
}
