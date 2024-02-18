package com.example.houses.services;

import com.example.houses.entity.House;
import com.example.houses.entity.HouseData;

import java.util.List;

public interface HouseService {

    List<House> getAll();
    House createHouse(HouseData house);
    House getHouseById(Integer id);
    void removeHouse(Integer id);
    House updateHouse(Integer id, HouseData house);

}
