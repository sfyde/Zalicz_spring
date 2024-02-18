package com.example.houses.controller;

import com.example.houses.entity.House;
import com.example.houses.entity.HouseData;
import com.example.houses.services.HouseService;
import com.example.houses.services.HouseServiceDao;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/houses")
public class HouseController {

        private final HouseService houseService;

        @GetMapping()
        public ResponseEntity<List<House>> getHouses() {
            List<House> houses = houseService.getAll();
            return ResponseEntity.ok(houses);
        }

        @PostMapping("/add")
        public ResponseEntity<House> createHouse(@RequestBody HouseData houseData) {
            House newHouse = houseService.createHouse(houseData);
            return ResponseEntity.status(HttpStatus.CREATED).body(newHouse);
        }

        @GetMapping("/{id}")
        public ResponseEntity<House> getHouseById(@PathVariable Integer id) {
            House house = houseService.getHouseById(id);
            return ResponseEntity.ok(house);
        }

        @DeleteMapping("/remove/{id}")
        public ResponseEntity<?> removeHouse(@PathVariable Integer id) {
            houseService.removeHouse(id);
            return ResponseEntity.ok().build();
        }

        @PatchMapping("/update/{id}")
        public ResponseEntity<House> updateHouse(@PathVariable Integer id, @RequestBody HouseData changesToHouse) {
            House updatedHouse = houseService.updateHouse(id, changesToHouse);
            if (changesToHouse != null) {
                return ResponseEntity.ok(updatedHouse);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
}
