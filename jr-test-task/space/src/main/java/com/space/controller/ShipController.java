package com.space.controller;

import com.space.model.Ship;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "rest/ships")
public class ShipController {

    @Autowired
    private ShipService service;

    @GetMapping
    public ResponseEntity<List<Ship>> getShipsList(@RequestParam Map<String, String> allParams) {
        List<Ship> ships = service.getShipsList(allParams);

        return new ResponseEntity<>(ships, HttpStatus.OK);
    }

    @GetMapping("count")
    public ResponseEntity<Integer> getShipsCount(@RequestParam Map<String, String> allParams) {
        Integer count = service.getShipsCount(allParams);

        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Ship> getShip(@PathVariable Integer id) {
        if (id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Ship ship = null;

        try {
            ship = service.getShip(id);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(ship, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Ship> createShip(@RequestBody Ship ship) {
        Ship newShip = null;

        try {
            newShip = service.createShip(ship);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(newShip, HttpStatus.OK);
    }

    @PostMapping("{id}")
    public ResponseEntity<Ship> updateShip(@PathVariable Integer id, @RequestBody Ship ship) {
        if (id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ship.setId(id.longValue());

        Ship newShip = null;

        try {
            newShip = service.updateShip(ship);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(newShip, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteShip(@PathVariable Integer id) {
        if (id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            service.deleteShip(id);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
