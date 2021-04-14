package com.slinemotors.slineproject.service.impl;

import com.slinemotors.slineproject.entity.Employee;
import com.slinemotors.slineproject.exceptions.ForbiddenException;
import com.slinemotors.slineproject.exceptions.NotFoundException;
import com.slinemotors.slineproject.repository.EmployeeRepository;
import com.slinemotors.slineproject.service.EmployeeService;
import com.slinemotors.slineproject.service.PositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private PositionService positionService;

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private AuthorizeInfo authorizeInfo;

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<Employee> findAll() {
        return repository.findAll();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Override
    public Employee findById(long id) {
        if (!authorizeInfo.isUserEmployeeOwner(id)) {
            //TODO:
            throw new ForbiddenException("Die!");
        }

        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public Employee create(Employee employee) {
        employee.setFired(false);

        Employee createdEmployee = repository.save(employee);

        log.info("IN create - employee: {} successfully created", createdEmployee);

        return createdEmployee;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Override
    public Employee update(long id, Employee newEmployee) {
        return repository.findById(id)
                .map(employee -> {

                    if (!authorizeInfo.isUserEmployeeOwner(id)) {
                        String message = String.format("You haven't permission to edit employee with id %d", id);

                        log.warn("IN update - employee: {} forbidden update for user (login) {}",
                                newEmployee,
                                authorizeInfo.getAuthorizedUser().getLogin());

                        throw new ForbiddenException(message);
                    }

                    boolean isAdmin = authorizeInfo.isAdmin();

                    employee.setFirstName(newEmployee.getFirstName());
                    employee.setSecondName(newEmployee.getSecondName());
                    employee.setLastName(newEmployee.getLastName());
                    employee.setPassportCode(newEmployee.getPassportCode());
                    employee.setPassportIssuedBy(newEmployee.getPassportIssuedBy());
                    employee.setPhone(newEmployee.getPhone());
                    employee.setLocation(newEmployee.getLocation());

                    if (isAdmin) {
                        employee.setCode(newEmployee.getCode());
                        employee.setFired(newEmployee.isFired());
                        employee.setPosition(positionService.findById(newEmployee.getPosition().getId()));
                    }

                    Employee updatedEmployee = repository.save(employee);

                    log.info("IN update - employee: {} successfully updated", updatedEmployee);

                    return updatedEmployee;
                })
                .orElseGet(() -> {
                    if (authorizeInfo.isAdmin()) {
                        newEmployee.setId(id);

                        Employee createdEmployee = repository.save(newEmployee);

                        log.info("IN update - employee: {} not found. Created new one with id - {}", createdEmployee, id);

                        return createdEmployee;
                    } else {
                        log.info("IN update - employee id: {} not found.", id);

                        throw new NotFoundException("Employee not found");
                    }
                });
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void delete(long id) {
        repository.deleteById(id);

        log.info("IN delete - employee: {} was deleted", id);
    }
}
