package com.wy.operating_room.controller;

import com.wy.operating_room.entity.*;
import com.wy.operating_room.service.MedicalWorkerService;
import com.wy.operating_room.service.OperationService;
import com.wy.operating_room.service.PatientService;
import com.wy.operating_room.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/status")
public class StatusController {

	@Autowired
	StatusService statusService;
	@Autowired
	PatientService patientService;
	@Autowired
	MedicalWorkerService medicalWorkerService;
	@Autowired
	OperationService operationService;

	@GetMapping("/list")
	public Result list(int page, int limit) {
		Result result = new Result();
		List<Status> data;
		List<Status> pageData;
		data = statusService.findAll();
		pageData = statusService.findPage((page - 1) * limit, limit);
		for (Status status : pageData) {
			Patient patient = patientService.findById(Integer.parseInt(status .getPid()));
			MedicalWorker medicalWorker = medicalWorkerService.findById(Integer.parseInt(status .getUid()));
			if (patient != null) {
				status.setPname(patient.getName());
				status.setUname(medicalWorker.getName());
			}
		}
		result.setCode(0);
		result.setMsg("SUCCESS");
		result.setCount(data.size());
		result.setData(pageData);
		return result;
	}

	@GetMapping("/now")
	public Result now(String location) {
		Result result = new Result();
		List<Status> data;
		String oid = operationService.findNowByLocation(location).getId().toString();
		data = statusService.findNews(oid,5);
		for (Status status : data) {
			Patient patient = patientService.findById(Integer.parseInt(status .getPid()));
			MedicalWorker medicalWorker = medicalWorkerService.findById(Integer.parseInt(status .getUid()));
			if (patient != null) {
				status.setPname(patient.getName());
				status.setUname(medicalWorker.getName());
			}
		}
		result.setCode(0);
		result.setMsg("SUCCESS");
		result.setCount(data.size());
		result.setData(data);
		return result;
	}

	@GetMapping("/showData")
	public Result showData(String oid) {
		Result result = new Result();
		List<Status> data;
		data = statusService.findData(oid);
		result.setCode(0);
		result.setMsg("SUCCESS");
		result.setCount(data.size());
		result.setData(data);
		return result;
	}
}
