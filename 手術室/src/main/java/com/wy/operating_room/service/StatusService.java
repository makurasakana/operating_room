package com.wy.operating_room.service;

import org.springframework.stereotype.Service;
import com.wy.operating_room.entity.Result;
import com.wy.operating_room.entity.ResultData;
import com.wy.operating_room.entity.Status;
import com.wy.operating_room.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.util.List;

@Service
public class StatusService{

	@Autowired
	private StatusRepository statusRepository;

	
	public Status findById(int id){
		Status one = statusRepository.findById(id).orElse(null);
		return one;
	}

	
	public ResponseEntity deleteOne(int id){
//		statusRepository.deleteById(id);
		statusRepository.wasteById(id);
		return ResultData.success();
	}

	
	public Result saveOne(Status entity){
		Result result = new Result();
		Status one = statusRepository.save(entity);
		result.setData(one);
		return result;
	}

	
	public List<Status> findAll() {return statusRepository.findAll(); }
    
    public List<Status> findByPid(String pid) {return statusRepository.findByPid(pid); }
	
	public List<Status> findByOid(String oid) {return statusRepository.findByOid(oid); }

	public List<Status> findByUid(String uid) {return statusRepository.findByUid(uid); }

	
	public List<Status> findPage(int page, int limit){
		List<Status> StatusList = statusRepository.findPage(page,limit);
		return StatusList;
	}
    
    public List<Status> findPageByPid(String pid, int page, int limit){
        List<Status> StatusList = statusRepository.findPageByPid(pid, page,limit);
        return StatusList;
    }
	
	public List<Status> findPageByOid(String oid, int page, int limit){
		List<Status> StatusList = statusRepository.findPageByOid(oid, page,limit);
		return StatusList;
	}

	public List<Status> findPageByUid(String uid, int page, int limit){
		List<Status> StatusList = statusRepository.findPageByUid(uid, page,limit);
		return StatusList;
	}

	public Status findLastOne() {return statusRepository.findLastOne(); }

	public List<Status> findData(String oid) {return statusRepository.findData(oid); }
}
