package com.wy.operating_room.repository;

import com.wy.operating_room.entity.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeptRepository extends JpaRepository<Dept, Integer> {
	@Query(value = "select * from dept limit ?1,?2", nativeQuery = true)
	public List<Dept> findPage(Integer page, Integer limit);
}
