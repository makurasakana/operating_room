package com.wy.operating_room.repository;

import com.wy.operating_room.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRepository extends JpaRepository<Account, String>, JpaSpecificationExecutor {
    public Account findByUsername(String username);

    @Query(value = "select title from sys_orgunit where id = ?1", nativeQuery = true)
    public String findOrg(String username);
}
