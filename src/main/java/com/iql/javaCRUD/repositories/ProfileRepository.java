package com.iql.javaCRUD.repositories;

import com.iql.javaCRUD.models.Phone;
import com.iql.javaCRUD.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    List<Profile> findByAge(int age);
    List<Profile> findByIncGreaterThan(int id);
    boolean existsByUserEmail(String user_email);
}

