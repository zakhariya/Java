package com.slinemotors.slineproject.service.impl;

import com.slinemotors.slineproject.entity.Role;
import com.slinemotors.slineproject.entity.User;
import com.slinemotors.slineproject.exceptions.ForbiddenException;
import com.slinemotors.slineproject.exceptions.NotFoundException;
import com.slinemotors.slineproject.repository.UserRepository;
import com.slinemotors.slineproject.service.RoleService;
import com.slinemotors.slineproject.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorizeInfo authorizeInfo;

    @Value("${var.default.password}")
    private String defaultPassword;

    @Value("${var.admin.role}")
    private String adminRole;

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Override
    public User findById(long id) {
        System.out.println(authorizeInfo.isUserSelfOwner(id));
        if (!authorizeInfo.isUserSelfOwner(id)) {
            //TODO:
            throw new ForbiddenException("You have no permission to this!!!");
        }

        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public User create(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);

        User createdUser = repository.save(user);

        log.info("IN create - user: {} successfully created", createdUser);

        return createdUser;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Override
    //TODO: Think about again
    public User update(long id, User newUser) {
        return repository.findById(id)
                .map(user -> {
                    boolean isAdmin = authorizeInfo.isAdmin();

                    if (isAdmin || !authorizeInfo.isUserSelfOwner(id)) {
                        String message = String.format("You haven't permission to edit user with id %d", id);

                        log.warn("IN update - user: {} forbidden update for user (login) {}",
                                newUser,
                                authorizeInfo.getAuthorizedUser().getLogin());

                        throw new ForbiddenException(message);
                    }

                    user.setLogin(newUser.getLogin());
                    user.setPassword(passwordEncoder.encode(newUser.getPassword()));
                    user.setEmail(newUser.getEmail());


                    if (isAdmin) {
                        user.setRoles(newUser.getRoles());
                        user.setEnabled(newUser.isEnabled());
                        user.setEmployee(newUser.getEmployee());
                    }

                    User savedUser = repository.save(user);

                    log.info("IN update - user: {} successfully updated", savedUser);

                    return savedUser;
                })
                .orElseGet(() -> {
                    if (authorizeInfo.isAdmin()) {
                        newUser.setId(id);
                        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

                        User createdUser = repository.save(newUser);

                        log.info("IN update - user: {} not found. Created new one with id - {}", createdUser, id);

                        return createdUser;
                    } else {
                        throw new NotFoundException("User not found");
                    }
                });
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void delete(long id) {
        repository.deleteById(id);

        log.info("IN delete - user: {} was deleted", id);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return repository.findByLogin(login).orElseThrow(() ->
                new UsernameNotFoundException("user " + login + " was not found!"));
    }

    @PostConstruct
    public void init() {
        if (repository.findAll().size() == 0) {
            User user = new User();

            user.setLogin("admin");
            user.setPassword(passwordEncoder.encode(defaultPassword));
            user.setRoles(getAdminRoles());
            user.setEnabled(true);

            User createdUser = repository.save(user);

            log.warn("IN init - user: {} successfully created with default password: {}", createdUser, defaultPassword);
        } else {
            repository.findByLogin("admin").ifPresent(user -> {
                user.setPassword(passwordEncoder.encode(defaultPassword));
                user.setRoles(getAdminRoles());
                user.setEnabled(true);

                User updatedUser = repository.save(user);

                log.info("IN init - user: {} successfully updated with default password: {}. Please change login.", updatedUser, defaultPassword);
            });
        }
    }

    private Set<Role> getAdminRoles() {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findByName(adminRole));

        return roles;
    }
}