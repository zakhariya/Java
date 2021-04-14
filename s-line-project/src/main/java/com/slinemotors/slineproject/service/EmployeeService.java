package com.slinemotors.slineproject.service;

import com.slinemotors.slineproject.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();

    Employee findById(long id);

    Employee create(Employee employee);

    Employee update(long id, Employee employee);

    void delete(long id);
}
