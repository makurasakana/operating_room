package com.wy.operating_room.controller;

import com.wy.operating_room.entity.Dept;
import com.wy.operating_room.entity.Result;
import com.wy.operating_room.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dept")
public class DeptController {

	@Autowired
	DeptService deptService;

	@GetMapping("/list")
	public Result list(int page, int limit) {
		Result result = new Result();
		List<Dept> data;
		List<Dept> pageData;
		data = deptService.findAll();
		pageData = deptService.findPage((page - 1) * limit, limit);
		result.setCode(0);
		result.setMsg("SUCCESS");
		result.setCount(data.size());
		result.setData(pageData);
		return result;
	}
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void addDept(@RequestBody Dept entity) {deptService.saveOne(entity); }
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void deleteDept(@RequestParam("id ") int id) {
		 deptService.deleteOne(id);
	}
	@RequestMapping(value = "/deleteSelected", method = RequestMethod.POST)
	public void deleteSelectedBox(String ids) {
		if (ids != null && !ids.equals("")) {
			String[] sids = ids.split(",");
			for (String sid : sids) {
				if (sid != null && !sid.equals("")) {
					deptService.deleteOne(Integer.parseInt(sid));
				}
			}
		}
	}

	@GetMapping("/getDept")
	public List<Dept> getDept() {
		return deptService.findAll();
	}
}
