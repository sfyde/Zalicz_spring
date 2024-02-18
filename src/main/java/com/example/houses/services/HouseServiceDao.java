package com.example.houses.services;

import com.example.houses.entity.House;
import com.example.houses.entity.HouseData;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Component
@Profile("dao")
public class HouseServiceDao implements HouseService{
    private final List<House> houses = new ArrayList<>();
    @Override
    public List<House> getAll() {
        return houses;
    }

    @Override
    public House createHouse(HouseData house) {
        if (house.getAddress().isEmpty()) {
            throw new IllegalArgumentException("Address is empty");
        }
        if (house.getPrice()<0) {
            throw new IllegalArgumentException("Price can't be set bellow 0");
        }
        if (house.getSize()<0) {
            throw new IllegalArgumentException("Size can't be set bellow 0");
        }
        if (house.getRooms()<0) {
            throw new IllegalArgumentException("Number of rooms can't be set bellow 0");
        }
        House newHouse = new House();
        newHouse.setId(houses.size()+1);
        newHouse.setAddress(house.getAddress());
        newHouse.setPrice(house.getPrice());
        newHouse.setSize(house.getSize());
        newHouse.setRooms(house.getRooms());
        houses.add(newHouse);
        return newHouse;
    }

    @Override
    public House getHouseById(Integer id) {
        return houses.get(id);
    }

    @Override
    public void removeHouse(Integer id) {
        houses.removeIf(houses-> houses.getId().equals(id));
    }

    @Override
    public House updateHouse(Integer id, HouseData updatedHouse) {
        Optional<House> toBeUpdatedHouse = houses.stream()
                .filter(house -> house.getId().equals(id))
                .findFirst();

        toBeUpdatedHouse.ifPresent(house -> {
            if (updatedHouse.getAddress() != null) {
                house.setAddress(updatedHouse.getAddress());
            }
            if (updatedHouse.getPrice() != null) {
                house.setPrice(updatedHouse.getPrice());
            }
            if (updatedHouse.getSize() != null) {
                house.setSize(updatedHouse.getSize());
            }
            if (updatedHouse.getRooms() != null) {
                house.setRooms(updatedHouse.getRooms());
            }

        });

        return toBeUpdatedHouse.orElse(null);
    }
}
