package com.iql.javaCRUD.repositories;

import com.iql.javaCRUD.models.Phone;
import com.iql.javaCRUD.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
}

