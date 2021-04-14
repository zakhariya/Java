package com.slinemotors.slineproject.service.impl;

import com.slinemotors.slineproject.entity.Employee;
import com.slinemotors.slineproject.entity.Role;
import com.slinemotors.slineproject.entity.User;
import com.slinemotors.slineproject.service.EmployeeService;
import com.slinemotors.slineproject.service.RoleService;
import com.slinemotors.slineproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
class AuthorizeInfo {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    @Value("${var.admin.role}")
    private String adminRole;

    //TODO: remove
    public void ttt() {
        String n = SecurityContextHolder.getContext().getAuthentication().getName();
//        String c = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        String d = SecurityContextHolder.getContext().getAuthentication().getDetails().toString();
        String p = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        System.out.println(n);
//        System.out.println(c);
        System.out.println(d);
        System.out.println(p);
    }

    public User getAuthorizedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Collection<? extends GrantedAuthority> getUserAuthorities() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }

    public boolean isAdmin() {
        Role adminRole = roleService.findByName(this.adminRole);
        Collection<? extends GrantedAuthority> authorities = getUserAuthorities();

        return authorities.contains(adminRole);
    }

    public boolean isUserSelfOwner(long userId) {
        User user = getAuthorizedUser();

        return user.getId() == userId;
    }

    public boolean isUserEmployeeOwner(long employeeId) {
        User user = getAuthorizedUser();
        Employee employee = user.getEmployee();

        return employee != null && employee.getId() == employeeId;
    }
}
