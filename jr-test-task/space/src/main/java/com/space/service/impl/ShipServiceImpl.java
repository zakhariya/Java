package com.space.service.impl;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ShipServiceImpl implements ShipService {

    @Autowired
    private ShipRepository repository;

    @Override
    public List<Ship> getShipsList(Map<String, String> allParams) {
        RequestParametersParser parser = new RequestParametersParser(allParams);

        String name = parser.getShipName();
        String planet = parser.getShipPlanet();
        ShipType shipType = parser.getShipType();
        Date after = parser.getAfter();
        Date before = parser.getBefore();
        Boolean isUsed = parser.getSheepUsed();
        Double minSpeed = parser.getShipMinSpeed();
        Double maxSpeed = parser.getShipMaxSpeed();
        Integer minCrewSize = parser.getShipMinCrewSize();
        Integer maxCrewSize = parser.getShipMaxCrewSize();
        Double minRating = parser.getShipMinRating();
        Double maxRating = parser.getShipMaxRating();
        Pageable pageable = parser.getPageable();

        return repository.findBy(name, planet, shipType, after, before, isUsed, minSpeed,
                maxSpeed,minCrewSize, maxCrewSize, minRating, maxRating, pageable);
    }

    @Override
    public Integer getShipsCount(Map<String, String> allParams) {
        RequestParametersParser parser = new RequestParametersParser(allParams);

        String name = parser.getShipName();
        String planet = parser.getShipPlanet();
        ShipType shipType = parser.getShipType();
        Date after = parser.getAfter();
        Date before = parser.getBefore();
        Boolean isUsed = parser.getSheepUsed();
        Double minSpeed = parser.getShipMinSpeed();
        Double maxSpeed = parser.getShipMaxSpeed();
        Integer minCrewSize = parser.getShipMinCrewSize();
        Integer maxCrewSize = parser.getShipMaxCrewSize();
        Double minRating = parser.getShipMinRating();
        Double maxRating = parser.getShipMaxRating();

        return repository.getShipsCount(name, planet, shipType, after, before, isUsed, minSpeed,
                maxSpeed,minCrewSize, maxCrewSize, minRating, maxRating);
    }

    @Override
    public Ship getShip(Integer id) {
        Ship ship = null;

        try {
            ship = repository.findById(Long.valueOf(id)).get();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException();
        }

        return ship;
    }

    @Override
    public Ship createShip(Ship ship) {
        Ship newShip = new Ship();

        int minProdYear = 2800;
        long maxProdYear = 3019;

        double minSpeed = 0.01;
        double maxSpeed = 0.99;

        int minCrewSize = 1;
        int maxCrewSize = 9999;

        String shipName = ship.getName();
        String shipPlanet = ship.getPlanet();
        ShipType shipType = ship.getShipType();
        Date shipProdDate = ship.getProdDate();
        Double shipSpeed = ship.getSpeed();
        Integer shipCrewSize = ship.getCrewSize();
        Boolean shipUsed = ship.getUsed();

        if (shipName == null || shipPlanet == null || shipProdDate == null
                || shipSpeed == null || shipCrewSize == null) {
            throw new IllegalArgumentException();
        }

        if (shipName.length() > 0 && shipName.length() <= 50
                && shipPlanet.length() > 0 && shipPlanet.length() <= 50) {

            newShip.setName(shipName);
            newShip.setPlanet(shipPlanet);
        } else {
            throw new IllegalArgumentException();
        }


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(shipProdDate);

        int productionYear = calendar.get(Calendar.YEAR);

        if (productionYear >= minProdYear && productionYear <= maxProdYear) {
            newShip.setProdDate(shipProdDate);
        } else {
            throw new IllegalArgumentException();
        }

        if (shipSpeed.doubleValue() >= minSpeed && shipSpeed.doubleValue() <= maxSpeed
                && shipCrewSize.intValue() >= minCrewSize && shipCrewSize.intValue() <= maxCrewSize) {

            BigDecimal speed =
                    new BigDecimal(shipSpeed.doubleValue()).setScale(2, BigDecimal.ROUND_HALF_EVEN);

            newShip.setSpeed(speed.doubleValue());
            newShip.setCrewSize(shipCrewSize);
        } else {
            throw new IllegalArgumentException();
        }

        double koef = 1.0;

        if (shipUsed != null && shipUsed.booleanValue() == true) {
            koef = 0.5;
            newShip.setUsed(true);
        } else {
            newShip.setUsed(false);
        }


        double rating = (80 * shipSpeed.doubleValue() * koef) / (maxProdYear - productionYear + 1);

        rating = new BigDecimal(rating).setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();

        newShip.setRating(rating);
        newShip.setShipType(shipType);


        return repository.save(newShip);
    }


    @Override
    public Ship updateShip(Ship ship) {
        long id = ship.getId();
        Ship newShip = null;

        try {
            newShip = repository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException();
        }

        int minProdYear = 2800;
        long maxProdYear = 3019;

        double minSpeed = 0.01;
        double maxSpeed = 0.99;

        int minCrewSize = 1;
        int maxCrewSize = 9999;

        String shipName = ship.getName();
        String shipPlanet = ship.getPlanet();
        ShipType shipType = ship.getShipType();
        Date shipProdDate = ship.getProdDate();
        Double shipSpeed = ship.getSpeed();
        Integer shipCrewSize = ship.getCrewSize();
        Boolean shipUsed = ship.getUsed();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(newShip.getProdDate());

        int productionYear = calendar.get(Calendar.YEAR);

        if (shipName != null) {
            if (shipName.length() > 0 && shipName.length() <= 50) {
                newShip.setName(shipName);
            } else {
                throw new IllegalArgumentException();
            }
        }

        if (shipPlanet != null) {
            if (shipPlanet.length() > 0 && shipPlanet.length() <= 50) {
                newShip.setPlanet(shipPlanet);
            } else {
                throw new IllegalArgumentException();
            }
        }

        if (shipType != null) {
            newShip.setShipType(shipType);
        }

        if (shipProdDate != null) {
            calendar.setTime(shipProdDate);
            productionYear = calendar.get(Calendar.YEAR);

            if (productionYear >= minProdYear && productionYear <= maxProdYear) {
                newShip.setProdDate(shipProdDate);
            } else {
                throw new IllegalArgumentException();
            }
        }

        if (shipSpeed != null) {
            if (shipSpeed.doubleValue() >= minSpeed && shipSpeed.doubleValue() <= maxSpeed) {
                BigDecimal speed =
                        new BigDecimal(shipSpeed.doubleValue()).setScale(2, BigDecimal.ROUND_HALF_EVEN);

                newShip.setSpeed(speed.doubleValue());
            } else {
                throw new IllegalArgumentException();
            }
        }

        if (shipCrewSize != null) {
            if (shipCrewSize.intValue() >= minCrewSize && shipCrewSize.intValue() <= maxCrewSize) {
                newShip.setCrewSize(shipCrewSize);
            } else {
                throw new IllegalArgumentException();
            }
        }

        if (shipUsed != null) {
            newShip.setUsed(shipUsed);
        }

        boolean used = newShip.getUsed().booleanValue();
        double koef = used ? 0.5 : 1.0;

        double rating = (80 * newShip.getSpeed().doubleValue() * koef) / (maxProdYear - productionYear + 1);

        rating = new BigDecimal(rating).setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();

        newShip.setRating(rating);

        return repository.save(newShip);
    }

    @Override
    public void deleteShip(Integer id) {
        Ship ship = null;

        try {
            ship = repository.findById(Long.valueOf(id)).get();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException();
        }

        repository.delete(ship);
    }
}
