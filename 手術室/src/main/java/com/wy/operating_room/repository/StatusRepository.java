package com.wy.operating_room.repository;

import com.wy.operating_room.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer>{
    public List<Status> findByPid(String pid);
    public List<Status> findByOid(String oid);
    @Query(value = "select * from status where uid = ?1", nativeQuery = true)
    public List<Status> findByUid(String uid);
	@Query(value = "select * from status limit ?1,?2", nativeQuery = true)
	public List<Status> findPage(Integer page, Integer limit);

    @Query(value = "select * from status where pid = ?1 limit ?2,?3", nativeQuery = true)
    public List<Status> findPageByPid(String pid, Integer page, Integer limit);
    @Query(value = "select * from status where oid = ?1 limit ?2,?3", nativeQuery = true)
    public List<Status> findPageByOid(String oid, Integer page, Integer limit);
    @Query(value = "select * from status where uid = ?1 limit ?2,?3", nativeQuery = true)
    public List<Status> findPageByUid(String uid, Integer page, Integer limit);

    @Query(value = "select * from status order by id desc limit 1", nativeQuery = true)
    public Status findLastOne();

    @Transactional
    @Modifying
    @Query(value = "update status set wasted = '是' where id = ?1", nativeQuery = true)
    public void wasteById(Integer id);

    @Query(value = "select time as reportTime,systolicPressure,diastolicPressure,heartRate,breath,temperature,spo2 from status where oid = ?1", nativeQuery = true)
    public List<Status> findData(String oid);
}
