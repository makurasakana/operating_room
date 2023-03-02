package com.wy.operating_room.repository;

import com.wy.operating_room.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface LoginRepository extends JpaRepository<Login, String>, JpaSpecificationExecutor {

    Optional<Login> findByToken(String token);
}
