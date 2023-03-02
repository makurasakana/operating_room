package com.wy.operating_room.controller;

import com.wy.operating_room.entity.Patient;
import com.wy.operating_room.entity.Result;
import com.wy.operating_room.repository.PatientRepository;
import com.wy.operating_room.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

	@Autowired
	PatientService patientService;
	@Autowired
	PatientRepository patientRepository;

//	@GetMapping("num")
//	public Result num() {
//		return Result.setData(patientRepository.count());
//	}

	@GetMapping("/list")
	public Result list(int page, int limit) {
		Result result = new Result();
		List<Patient> data;
		List<Patient> pageData;
		data = patientService.findAll();
		pageData = patientService.findPage((page - 1) * limit, limit);
		result.setCode(0);
		result.setMsg("SUCCESS");
		result.setCount(data.size());
		result.setData(pageData);
		return result;
	}

	@GetMapping("/search")
	public Result search(String name, int page, int limit) {
		Result result = new Result();
		List<Patient> data;
		List<Patient> pageData;
		data = patientService.findByNameLike(name);
		pageData = patientService.findPageByNameLike(name,(page - 1) * limit, limit);
		result.setCode(0);
		result.setMsg("SUCCESS");
		result.setCount(data.size());
		result.setData(pageData);
		return result;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void addPatient(@RequestBody Patient entity) {patientService.saveOne(entity); }

	@GetMapping("/detail/{id}")
	public Result detail(@PathVariable String id) {
		Result result = new Result();
		result.setData(patientRepository.findById(Integer.valueOf(id)));
		return result;
	}

	@GetMapping("/delete/{id}")
	public void delete(@PathVariable String id)  {
		 patientService.deleteOne(Integer.valueOf(id));
	}
	@RequestMapping(value = "/deleteSelected", method = RequestMethod.POST)
	public void deleteSelectedBox(String ids) {
		if (ids != null && !ids.equals("")) {
			String[] sids = ids.split(",");
			for (String sid : sids) {
				if (sid != null && !sid.equals("")) {
					patientService.deleteOne(Integer.parseInt(sid));
				}
			}
		}
	}

}
