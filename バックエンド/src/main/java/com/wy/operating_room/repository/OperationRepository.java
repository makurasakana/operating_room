package com.wy.operating_room.repository;

import com.wy.operating_room.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Integer> {
	public List<Operation> findByPid(String pid);
	@Query(value = "select * from operation limit ?1,?2", nativeQuery = true)
	public List<Operation> findPage(Integer page, Integer limit);

	@Query(value = "select * from operation where pid = ?1 limit ?2,?3", nativeQuery = true)
	public List<Operation> findPageByPid(String pid, Integer page, Integer limit);

	@Query(value = "select * from operation where circulating_nurse = ?1 and status != '流程结束'", nativeQuery = true)
	public Operation current(String nickname);

	@Transactional
	@Modifying
	@Query(value = "update operation set status = ?1 where id = ?2", nativeQuery = true)
	public void saveState(String state, Integer oid);

	@Query(value = "select * from operation where status != '已结束'", nativeQuery = true)
	public List<Operation> getOnOperation();

	@Query(value = "select * from operation where location = ?1 and status != '已结束'", nativeQuery = true)
	public Operation findNowByLocation(String location);
}
