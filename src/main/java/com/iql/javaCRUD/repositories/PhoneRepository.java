package com.iql.javaCRUD.repositories;

import com.iql.javaCRUD.models.Phone;
import com.iql.javaCRUD.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {

    Phone findByValue(String phone);
    boolean existsByValue(String phone);
    void deleteByValue(String phone);
}

