package com.example.silkpaytask.repository;

import com.example.silkpaytask.entities.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataRepository extends JpaRepository<UserData, Long > {

}
