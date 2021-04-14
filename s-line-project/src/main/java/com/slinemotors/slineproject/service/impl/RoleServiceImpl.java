package com.slinemotors.slineproject.service.impl;

import com.slinemotors.slineproject.entity.Role;
import com.slinemotors.slineproject.repository.RoleRepository;
import com.slinemotors.slineproject.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository repository;

    @Override
    public List<Role> findAll() {
        return repository.findAll();
    }

    @Override
    public Role findByName(String name) {
        return repository.findByName(name);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public Role create(Role role) {
        Role createdRole = repository.save(role);

        log.info("IN create - role: {} successfully created", createdRole);

        return createdRole;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public Role update(int id, Role role) {
        Role updatedRole = repository.save(role);

        log.info("IN update - role: {} successfully updated", updatedRole);

        return updatedRole;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void delete(int id) {
        repository.deleteById(id);

        log.info("IN delete - user: {} was deleted", id);
    }
}
