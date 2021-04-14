package com.slinemotors.slineproject.service;

import com.slinemotors.slineproject.entity.Role;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface RoleService {
    List<Role> findAll();

    Role findByName(String name);

    Role create(Role role);

    Role update(int id, Role role);

    void delete(int id);
}
