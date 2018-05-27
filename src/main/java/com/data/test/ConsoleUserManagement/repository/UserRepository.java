package com.data.test.ConsoleUserManagement.repository;

import com.data.test.ConsoleUserManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    Boolean existsByUsername(String username);
}
