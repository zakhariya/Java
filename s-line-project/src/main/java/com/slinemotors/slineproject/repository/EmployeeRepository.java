package com.slinemotors.slineproject.repository;

import com.slinemotors.slineproject.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
