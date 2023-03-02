package com.wy.operating_room.repository;

import com.wy.operating_room.entity.HttpLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface HttpLogRepository extends JpaRepository<HttpLog, String>, JpaSpecificationExecutor {
}
