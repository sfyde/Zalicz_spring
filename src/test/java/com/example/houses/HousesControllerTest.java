package com.example.houses;

import com.example.houses.repository.HouseRepository;
import com.example.houses.services.HouseServiceDb;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.houses.entity.House;
import com.example.houses.entity.HouseData;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class HousesControllerTest {

    @Mock
    private HouseRepository houseRepository;

    @InjectMocks
    private HouseServiceDb houseServiceDb;

    @Test
    void testCreateHouse(){
        HouseData house = new HouseData();
        house.setAddress("Beskidzka 35/43");
        house.setPrice(1000000L);
        house.setSize(61L);
        house.setRooms(3);

        House housedb = new House();
        housedb.setId(1);
        housedb.setAddress(house.getAddress());
        housedb.setPrice(house.getPrice());
        housedb.setSize(house.getSize());
        housedb.setRooms(house.getRooms());

        when(houseRepository.save(any(House.class))).thenReturn(housedb);
        House newHouse = houseServiceDb.createHouse(house);
        verify(houseRepository, times(1)).save(any(House.class));

        assert newHouse != null;
        assert newHouse.getId().equals(1);
        assert newHouse.getAddress().equals("Beskidzka 35/43");
        assert newHouse.getPrice().equals(1000000L);
        assert newHouse.getSize().equals(61L);
        assert newHouse.getRooms().equals(3);

    }
    @Test
    void testRemoveHouse() {
        Integer remove = 1;
        when(houseRepository.findById(remove)).thenReturn(Optional.of(new House()));
        houseServiceDb.removeHouse(remove);
        verify(houseRepository, times(1)).deleteById(remove);
    }
}
