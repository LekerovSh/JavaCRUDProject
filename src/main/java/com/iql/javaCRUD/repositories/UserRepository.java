package com.iql.javaCRUD.repositories;

import com.iql.javaCRUD.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteByEmail(String email);
}