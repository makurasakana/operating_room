package com.wy.operating_room.repository;

import com.wy.operating_room.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
	@Query(value = "select * from patient limit ?1,?2", nativeQuery = true)
	public List<Patient> findPage(Integer page, Integer limit);

	@Query(value = "select * from patient where name = ?1", nativeQuery = true)
	public List<Patient> findByName(String name);

	@Query(value = "select * from patient where name like CONCAT('%',?1,'%')", nativeQuery = true)
	public List<Patient> findByNameLike(String name);

	@Query(value = "select * from patient where name like CONCAT('%',?1,'%') limit ?2,?3", nativeQuery = true)
	public List<Patient> findPageByNameLike(String name,Integer page, Integer limit);

	@Query(value = "select * from patient where id = ?1", nativeQuery = true)
	public Patient findOneById(Integer id);
}
