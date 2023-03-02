package com.wy.operating_room.controller;

import com.wy.operating_room.entity.Dept;
import com.wy.operating_room.entity.Result;
import com.wy.operating_room.entity.Patient;
import com.wy.operating_room.service.DeptService;
import com.wy.operating_room.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

	@Autowired
	PatientService patientService;

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
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void addPatient(Patient entity) {patientService.saveOne(entity); }
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void deletePatient(@RequestParam("id ") int id) {
		 patientService.deleteOne(id);
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
