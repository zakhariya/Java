package com.slinemotors.slineproject.service.impl;

import com.slinemotors.slineproject.entity.Position;
import com.slinemotors.slineproject.exceptions.NotFoundException;
import com.slinemotors.slineproject.repository.PositionRepository;
import com.slinemotors.slineproject.service.PositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
@Service
@Slf4j
public class PositionServiceImpl implements PositionService {

    @Autowired
    private PositionRepository repository;

    @Override
    public List<Position> findAll() {
        return repository.findAll();
    }

    @Override
    public Position findById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Position not found"));
    }

    @Override
    public Position create(Position position) {
        Position newPosition = repository.save(position);

        log.info("IN create - position: {} successfully created", newPosition);

        return newPosition;
    }

    @Override
    public Position update(int id, Position position) {
        Position newPosition = repository.save(position);

        log.info("IN update - position: {} successfully updated", newPosition);

        return newPosition;
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);

        log.info("IN delete - position: {} was deleted", id);
    }
}
