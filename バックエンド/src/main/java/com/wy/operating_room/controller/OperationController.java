package com.wy.operating_room.controller;

import com.wy.operating_room.entity.Operation;
import com.wy.operating_room.entity.Patient;
import com.wy.operating_room.entity.Result;
import com.wy.operating_room.repository.OperationRepository;
import com.wy.operating_room.service.OperationService;
import com.wy.operating_room.service.PatientService;
import com.wy.operating_room.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/operation")
public class OperationController {

	@Autowired
	OperationService operationService;
	@Autowired
	PatientService patientService;
	@Autowired
	private OperationRepository operationRepository;

	@GetMapping("/list")
	public Result list(int page, int limit) {
		Result result = new Result();
		List<Operation> data;
		List<Operation> pageData;
			data = operationService.findAll();
			pageData = operationService.findPage((page - 1) * limit, limit);
		for (Operation operation : pageData) {
			Patient patient = patientService.findById(Integer.parseInt(operation.getPid()));
			if (patient != null) {
				operation.setPname(patient.getName());
				operation.setDept(patient.getDept());
				operation.setBed(patient.getBed());
			}
		}
		result.setCode(0);
		result.setMsg("SUCCESS");
		result.setCount(data.size());
		result.setData(pageData);
		return result;
	}
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void addOperation(@RequestBody Operation entity) {
		entity.setStartTime(new Date());
		entity.setStatus("手术准备");
		operationRepository.save(entity);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void deleteOperation(@RequestParam("id ") int id) {
		 operationService.deleteOne(id);
	}
	@RequestMapping(value = "/deleteSelected", method = RequestMethod.POST)
	public void deleteSelectedBox(String ids) {
		if (ids != null && !ids.equals("")) {
			String[] sids = ids.split(",");
			for (String sid : sids) {
				if (sid != null && !sid.equals("")) {
					operationService.deleteOne(Integer.parseInt(sid));
				}
			}
		}
	}

	@GetMapping("/getOnOperation")
	public List<Operation> getOnOperation() {
		List<Operation> operations = operationService.getOnOperation();
		return operations;
	}
}
