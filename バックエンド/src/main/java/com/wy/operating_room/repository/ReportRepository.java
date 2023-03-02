package com.wy.operating_room.repository;

import com.wy.operating_room.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
	public List<Report> findByUid(String uid);
	public List<Report> findByOid(String oid);
	@Query(value = "select * from report limit ?1,?2", nativeQuery = true)
	public List<Report> findPage(Integer page, Integer limit);

	@Query(value = "select * from report where uid = ?1 limit ?2,?3", nativeQuery = true)
	public List<Report> findPageByUid(String uid, Integer page, Integer limit);
	@Query(value = "select * from report where oid = ?1 limit ?2,?3", nativeQuery = true)
	public List<Report> findPageByOid(String oid, Integer page, Integer limit);

	@Query(value = "select * from report order by id desc limit ?1", nativeQuery = true)
	public List<Report> findNews(Integer limit);
	@Query(value = "select * from report where oid = ?2 order by id desc limit ?1", nativeQuery = true)
	public List<Report> findNewsByOid(Integer limit,String oid);
}
