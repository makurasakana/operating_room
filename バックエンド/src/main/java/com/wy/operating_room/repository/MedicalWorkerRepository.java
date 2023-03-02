package com.wy.operating_room.repository;

import com.wy.operating_room.entity.MedicalWorker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalWorkerRepository extends JpaRepository<MedicalWorker, Integer> {
	@Query(value = "select * from medical_worker limit ?1,?2", nativeQuery = true)
	public List<MedicalWorker> findPage(Integer page, Integer limit);

	@Query(value = "select DISTINCT * from medical_worker where name=?1", nativeQuery = true)
	public MedicalWorker findByName(String name);

	@Query(value = "select * from medical_worker where dept=?1", nativeQuery = true)
	public List<MedicalWorker> findByDept(String dept);

	@Query(value = "select * from medical_worker where dept=?1 limit ?2,?3", nativeQuery = true)
	public List<MedicalWorker> findPageByDept(String dept, Integer page, Integer limit);
}
