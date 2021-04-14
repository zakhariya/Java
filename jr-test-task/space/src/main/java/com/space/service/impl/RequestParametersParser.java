package com.space.service.impl;

import com.space.controller.ShipOrder;
import com.space.model.ShipType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.Map;

class RequestParametersParser {
    private final Map<String, String> allParams;

    RequestParametersParser(Map<String, String> allParams) {
        this.allParams = allParams;
    }

    String getShipName() {
        if (allParams.containsKey("name")) {
            return allParams.get("name");
        }

        return null;
    }

    String getShipPlanet() {
        if (allParams.containsKey("planet")) {
            return allParams.get("planet");
        }

        return null;
    }

    ShipType getShipType() {
        if (allParams.containsKey("shipType")) {
            try {
                String type = allParams.get("shipType");

                return ShipType.valueOf(type.toUpperCase());
            } catch (IllegalArgumentException e) {}
        }

        return null;
    }

    Date getAfter() {
        if (allParams.containsKey("after")) {
            try {
                Long date = Long.parseLong(allParams.get("after"));

                return new Date(date);
            } catch (NumberFormatException e) {}
        }

        return null;
    }

    Date getBefore() {
        if (allParams.containsKey("before")) {
            try {
                Long date = Long.parseLong(allParams.get("before"));
                return new Date(date);
            } catch (NumberFormatException e) {}
        }

        return null;
    }

    Boolean getSheepUsed() {
        if (allParams.containsKey("isUsed")) {
            return new Boolean(allParams.get("isUsed"));
        }

        return null;
    }

    Double getShipMinSpeed() {
        if (allParams.containsKey("minSpeed")) {
            try {
                return new Double(allParams.get("minSpeed"));
            } catch (NumberFormatException e) {}
        }

        return null;
    }

    Double getShipMaxSpeed() {
        if (allParams.containsKey("maxSpeed")) {
            try {
                return new Double(allParams.get("maxSpeed"));
            } catch (NumberFormatException e) {}
        }

        return null;
    }

    Integer getShipMinCrewSize() {
        if (allParams.containsKey("minCrewSize")) {
            try {
                return new Integer(allParams.get("minCrewSize"));
            } catch (NumberFormatException e) {}
        }

        return null;
    }

    Integer getShipMaxCrewSize() {
        if (allParams.containsKey("maxCrewSize")) {
            try {
                return new Integer(allParams.get("maxCrewSize"));
            } catch (NumberFormatException e) {}
        }

        return null;
    }

    Double getShipMinRating() {
        if (allParams.containsKey("minRating")) {
            try {
               return new Double(allParams.get("minRating"));
            } catch (NumberFormatException e) {}
        }

        return null;
    }

    Double getShipMaxRating() {
        if (allParams.containsKey("maxRating")) {
            try {
                return new Double(allParams.get("maxRating"));
            } catch (NumberFormatException e) {}
        }

        return null;
    }

    Pageable getPageable() {
        ShipOrder order = ShipOrder.valueOf("ID");
        Integer pageNumber = 0;
        Integer pageSize = 3;

        if (allParams.containsKey("order")) {
            try {
                String type = allParams.get("order");
                order = ShipOrder.valueOf(type.toUpperCase());
            } catch (IllegalArgumentException e) {}
        }
        if (allParams.containsKey("pageNumber")) {
            try {
                pageNumber = new Integer(allParams.get("pageNumber"));
            } catch (NumberFormatException e) {}
        }
        if (allParams.containsKey("pageSize")) {
            try {
                pageSize = new Integer(allParams.get("pageSize"));
            } catch (NumberFormatException e) {}
        }

        return PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()).ascending());
    }
}
