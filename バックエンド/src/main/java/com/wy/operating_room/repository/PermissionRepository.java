package com.wy.operating_room.repository;

import com.wy.operating_room.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {

    List<Permission> getByResource(String resource);

    Optional<Permission> findByResourceAndTarget(String resource, String target);
}
