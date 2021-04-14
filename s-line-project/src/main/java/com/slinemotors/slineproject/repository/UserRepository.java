package com.slinemotors.slineproject.repository;

import com.slinemotors.slineproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //TODO: remove
//    //@Query("SELECT * FROM Survey v GROUP BY v.answer")
//    List<Company> findAllByOrderByName();
//
//    //@Query("SELECT U.name FROM User U WHERE LOWER(U.name) LIKE LOWER(concat(?1, '%'))")
//    List<Company> findByNameContainingOrderByName(String name);

    Optional<User> findByLogin(String login);
}
