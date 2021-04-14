package com.slinemotors.slineproject.service;

import com.slinemotors.slineproject.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(long id);

    User create(User user);

    User update(long id, User user);

    void delete(long id);

    //TODO: remove
//    void createDefaultUser();
}
