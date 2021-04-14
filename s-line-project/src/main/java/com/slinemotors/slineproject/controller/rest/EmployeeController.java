package com.slinemotors.slineproject.controller.rest;

import com.slinemotors.slineproject.entity.Employee;
import com.slinemotors.slineproject.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "employees", produces = "application/vnd.s-line.api.v1+json")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        List<Employee> employees = employeeService.findAll();

        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Employee> getById(@PathVariable long id) {
        Employee employee = employeeService.findById(id);

        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {

        return new ResponseEntity<>(employeeService.create(employee), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Employee> update(@PathVariable long id, @RequestBody Employee employee) {

        return new ResponseEntity<>(employeeService.update(id, employee), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable long id) {
        employeeService.delete(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
