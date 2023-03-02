package com.wy.operating_room.controller;

import com.wy.operating_room.entity.MedicalWorker;
import com.wy.operating_room.entity.Patient;
import com.wy.operating_room.entity.Result;
import com.wy.operating_room.entity.Status;
import com.wy.operating_room.service.MedicalWorkerService;
import com.wy.operating_room.service.PatientService;
import com.wy.operating_room.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void addStatus(Status entity) {statusService.saveOne(entity); }
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void deleteStatus(@RequestParam("id") int id) {
		 statusService.deleteOne(id);
	}
	@RequestMapping(value = "/deleteSelected", method = RequestMethod.POST)
	public void deleteSelectedBox(String ids) {
		if (ids != null && !ids.equals("")) {
			String[] sids = ids.split(",");
			for (String sid : sids) {
				if (sid != null && !sid.equals("")) {
					statusService.deleteOne(Integer.parseInt(sid));
				}
			}
		}
	}

	@GetMapping("/myList")
	public Result myList(int page, int limit, HttpSession session) {
		Result result = new Result();
		List<Status> data;
		List<Status> pageData;
//		String uid = session.getAttribute("uid").toString();
		String uid = "1";
		data = statusService.findByUid(uid);
		pageData = statusService.findPageByUid(uid, (page - 1) * limit, limit);
		for (Status status : data) {
			Patient patient = patientService.findById(Integer.parseInt(status .getPid()));
			if (patient != null) {
				status.setPname(patient.getName());
			}
		}
		result.setCode(0);
		result.setMsg("SUCCESS");
		result.setCount(data.size());
		result.setData(pageData);
		return result;
	}

	@GetMapping("/operationList")
	public Result operationList(String oid) {
		Result result = new Result();
		List<Status> data;
		data = statusService.findByOid(oid);
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
