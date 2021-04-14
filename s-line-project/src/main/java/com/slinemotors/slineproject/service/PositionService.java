package com.slinemotors.slineproject.service;

import com.slinemotors.slineproject.entity.Position;

import java.util.List;

public interface PositionService {
    List<Position> findAll();

    Position findById(int id);

    Position create(Position position);

    Position update(int id, Position position);

    void delete(int id);
}
