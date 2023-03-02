package com.wy.operating_room.repository;

import com.wy.operating_room.entity.Orgunit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface OrgunitRepository extends JpaRepository<Orgunit, String>, JpaSpecificationExecutor {

    @Transactional
    @Modifying
    void deleteByIdpathLike(String id);
}
