package com.wy.operating_room.controller;

import com.wy.operating_room.entity.MedicalWorker;
import com.wy.operating_room.entity.Result;
import com.wy.operating_room.service.MedicalWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicalWorker")
public class MedicalWorkerController {

	@Autowired
	MedicalWorkerService Service;

	@GetMapping("/list")
	public Result list(int page, int limit) {
		Result result = new Result();
		List<MedicalWorker> data;
		List<MedicalWorker> pageData;
		data = Service.findAll();
		pageData = Service.findPage((page - 1) * limit, limit);
		result.setCode(0);
		result.setMsg("SUCCESS");
		result.setCount(data.size());
		result.setData(pageData);
		return result;
	}
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void addMedicalWorkers(@RequestBody MedicalWorker entity) {Service.saveOne(entity); }
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void deleteMedicalWorkers(@RequestParam("id ") int id) {
		 Service.deleteOne(id);
	}
	@RequestMapping(value = "/deleteSelected", method = RequestMethod.POST)
	public void deleteSelectedBox(String ids) {
		if (ids != null && !ids.equals("")) {
			String[] sids = ids.split(",");
			for (String sid : sids) {
				if (sid != null && !sid.equals("")) {
					Service.deleteOne(Integer.parseInt(sid));
				}
			}
		}
	}
	@GetMapping("/findByDept")
	public Result findByDept(String dept,int page, int limit) {
		Result result = new Result();
		List<MedicalWorker> data;
		List<MedicalWorker> pageData;
		data = Service.findByDept(dept);
		pageData = Service.findPageByDept(dept,(page - 1) * limit, limit);
		result.setCode(0);
		result.setMsg("SUCCESS");
		result.setCount(data.size());
		result.setData(pageData);
		return result;
	}
}
