package com.space.service;


import com.space.model.Ship;

import java.util.List;
import java.util.Map;

public interface ShipService {

    List<Ship> getShipsList(Map<String, String> allParams);

    Integer getShipsCount(Map<String, String> allParams);

    Ship getShip(Integer id);

    Ship createShip(Ship ship);

    Ship updateShip(Ship ship);

    void deleteShip(Integer id);
}
