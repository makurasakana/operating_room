package com.wy.operating_room.service;

import org.springframework.stereotype.Service;
import com.wy.operating_room.entity.Result;
import com.wy.operating_room.entity.ResultData;
import com.wy.operating_room.entity.MedicalWorker;
import com.wy.operating_room.repository.MedicalWorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.util.List;

@Service
public class MedicalWorkerService {

	@Autowired
	private MedicalWorkerRepository medicalWorkerRepository;

	
	public MedicalWorker findById(int id){
		MedicalWorker one = medicalWorkerRepository.findById(id).orElse(null);
		return one;
	}

	public MedicalWorker findByName(String name){
		MedicalWorker one = medicalWorkerRepository.findByName(name);
		return one;
	}
	
	public ResponseEntity deleteOne(int id){
		medicalWorkerRepository.deleteById(id);
		return ResultData.success();
	}

	
	public Result saveOne(MedicalWorker entity){
		Result result = new Result();
		MedicalWorker one = medicalWorkerRepository.save(entity);
		result.setData(one);
	return result;
	}

	
	public List<MedicalWorker> findAll() {return medicalWorkerRepository.findAll(); }

	
	public List<MedicalWorker> findPage(int page, int limit){
		List<MedicalWorker> medicalWorkerList = medicalWorkerRepository.findPage(page,limit);
		return medicalWorkerList;
	}

	public List<MedicalWorker> findByDept(String dept){
		return medicalWorkerRepository.findByDept(dept);
	}
	public List<MedicalWorker> findPageByDept(String dept,int page, int limit){
		List<MedicalWorker> medicalWorkerList = medicalWorkerRepository.findPageByDept(dept,page,limit);
		return medicalWorkerList;
	}
}
