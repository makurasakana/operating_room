package com.wy.operating_room.repository;

import com.wy.operating_room.entity.Exceptio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface ExceptioRepository extends JpaRepository<Exceptio, String>, JpaSpecificationExecutor {
}
