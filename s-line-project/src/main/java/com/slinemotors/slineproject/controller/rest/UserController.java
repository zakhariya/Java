package com.slinemotors.slineproject.controller.rest;

import com.slinemotors.slineproject.entity.User;
import com.slinemotors.slineproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "users", produces = "application/vnd.s-line.api.v1+json")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userService.findAll();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<User> getById(@PathVariable long id) {
        User user = userService.findById(id);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {

        return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> update(@PathVariable long id, @RequestBody User user) {

        return new ResponseEntity<>(userService.update(id, user), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable long id) {

        userService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}