package com.wy.operating_room.service;

import com.wy.operating_room.entity.Patient;
import com.wy.operating_room.entity.Result;
import com.wy.operating_room.entity.ResultData;
import com.wy.operating_room.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

	@Autowired
	private PatientRepository patientRepository;

	
	public Patient findById(int id){
		Patient one = patientRepository.findOneById(id);
		return one;
	}
	public List<Patient> findByName(String name){
		return patientRepository.findByName(name);
	}
	
	public ResponseEntity deleteOne(int id){
		patientRepository.deleteById(id);
		return ResultData.success();
	}

	
	public Result saveOne(Patient entity){
		Result result = new Result();
		Patient one = patientRepository.save(entity);
		result.setData(one);
	return result;
	}

	
	public List<Patient> findAll() {return patientRepository.findAll(); }


	
	public List<Patient> findPage(int page, int limit){
		List<Patient> PatientList = patientRepository.findPage(page,limit);
		return PatientList;
	}

	public List<Patient> findByNameLike(String name){
		List<Patient> PatientList = patientRepository.findByNameLike(name);
		return PatientList;
	}
	public List<Patient> findPageByNameLike(String name, int page, int limit){
		List<Patient> PatientList = patientRepository.findPageByNameLike(name,page,limit);
		return PatientList;
	}
}
