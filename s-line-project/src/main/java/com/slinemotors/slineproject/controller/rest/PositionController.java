package com.slinemotors.slineproject.controller.rest;

import com.slinemotors.slineproject.entity.Position;
import com.slinemotors.slineproject.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "positions", produces = "application/vnd.s-line.api.v1+json")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @GetMapping
    public ResponseEntity<Position> getAll() {
        List<Position> positions = positionService.findAll();

        return new ResponseEntity(positions, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Position> getById(@PathVariable int id) {
        Position position = positionService.findById(id);

        return new ResponseEntity(position, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Position> create(@RequestBody Position position) {

        return new ResponseEntity(positionService.create(position), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Position> update(@PathVariable int id, @RequestBody Position position) {

        return new ResponseEntity(positionService.update(id, position), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Position> delete(@PathVariable int id) {
        positionService.delete(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
