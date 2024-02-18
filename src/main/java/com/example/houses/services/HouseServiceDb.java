package com.example.houses.services;

import com.example.houses.entity.House;
import com.example.houses.entity.HouseData;
import com.example.houses.repository.HouseRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
@AllArgsConstructor
@Component
@Profile("db")
public class HouseServiceDb implements HouseService{

    private HouseRepository houseRepository;

    @Override
    public List<House> getAll() {
        return houseRepository.findAll();
    }

    @Override
    public House createHouse(HouseData house) {
        House newHouse = new House();
        newHouse.setAddress(house.getAddress());
        newHouse.setPrice(house.getPrice());
        newHouse.setRooms(house.getRooms());
        newHouse.setSize(house.getSize());
     return houseRepository.save(newHouse);
    }

    @Override
    public House getHouseById(Integer id) {
        return houseRepository.findById(id).orElseThrow();
    }

    @Override
    public void removeHouse(Integer id) {
        houseRepository.deleteById(id);
    }

    @Override
    public House updateHouse(Integer id, HouseData house) {
        House houseToBeUpdated = houseRepository.findById(id).orElse(null);
        if (houseToBeUpdated != null){
            if(house.getAddress()!= null){
                houseToBeUpdated.setAddress(house.getAddress());
            }
            if(house.getSize()!= null){
                houseToBeUpdated.setSize(house.getSize());
            }
            if(house.getPrice()!= null){
                houseToBeUpdated.setPrice(house.getPrice());
            }
            if(house.getRooms()!= null){
                houseToBeUpdated.setRooms(house.getRooms());
            }
            return houseRepository.save(houseToBeUpdated);
        }

        return null;
    }
}
