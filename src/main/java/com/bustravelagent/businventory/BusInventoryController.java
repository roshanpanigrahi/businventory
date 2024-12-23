package com.bustravelagent.businventory;

import com.bustravelagent.businventory.model.BusInventory;
import com.bustravelagent.businventory.repository.BusInventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class BusInventoryController {

    private static final Logger log = LoggerFactory.getLogger(BusInventoryController.class);

    BusInventoryRepository busInventoryRepository;

    public BusInventoryController(BusInventoryRepository busInventoryRepository) {
        this.busInventoryRepository = busInventoryRepository;
    }

    @GetMapping("/get/inventory/{busNo}")
    public ResponseEntity<BusInventory> getInventory(@PathVariable Integer busNo) {
        Optional<BusInventory> optionalBusInventory = Optional
                .ofNullable(busInventoryRepository.getBusInventoryByBusNo(busNo));
        return optionalBusInventory.map(ResponseEntity::ok)
                .orElseGet(() -> {
                    BusInventory badInventory = new BusInventory();
                    badInventory.setBusNo(0);
                    badInventory.setAvailableSeats(0);
                    badInventory.setLastUpdatedDate(Instant.MAX);
                    return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(badInventory);
                });

    }

    @PostMapping("/update/inventory/{busNo}/{seatsBooked}")
    public ResponseEntity<Void> updateInventory(@PathVariable Integer busNo, @PathVariable Integer seatsBooked) {
        //inventoryService.updateInventory(busRouteId, update);
        BusInventory inventory = busInventoryRepository.getBusInventoryByBusNo(busNo);
        if (inventory.getAvailableSeats() >= seatsBooked) {
            inventory.setAvailableSeats(inventory.getAvailableSeats() - seatsBooked);
            inventory.setLastUpdatedDate(Instant.now());
            busInventoryRepository.save(inventory);

        } else {
            log.info("Not enough seats available.");
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
