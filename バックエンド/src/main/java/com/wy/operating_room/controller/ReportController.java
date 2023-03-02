package com.wy.operating_room.controller;

import com.wy.operating_room.entity.MedicalWorker;
import com.wy.operating_room.entity.Operation;
import com.wy.operating_room.entity.Report;
import com.wy.operating_room.entity.Result;
import com.wy.operating_room.service.MedicalWorkerService;
import com.wy.operating_room.service.OperationService;
import com.wy.operating_room.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {

	@Autowired
	ReportService reportService;
	@Autowired
	OperationService operationService;
	@Autowired
	MedicalWorkerService medicalWorkerService;

	@GetMapping("/list")
	public Result list(int page, int limit) {
		Result result = new Result();
		List<Report> data;
		List<Report> pageData;
		data = reportService.findAll();
		pageData = reportService.findPage((page - 1) * limit, limit);
		for (Report report : pageData) {
			MedicalWorker medicalWorker = medicalWorkerService.findById(Integer.parseInt(report .getUid()));
			Operation operation = operationService.findById(Integer.parseInt(report .getOid()));
			if (medicalWorker != null) {
				report.setName(medicalWorker.getName());
				report.setLocation(operation.getLocation());
			}
		}
		result.setCode(0);
		result.setMsg("SUCCESS");
		result.setCount(data.size());
		result.setData(pageData);
		return result;
	}


	@GetMapping("/news")
	public Result news(int limit) {
		Result result = new Result();
		List<Report> data;
		data = reportService.findNews(limit);
		for (Report report : data) {
			MedicalWorker medicalWorker = medicalWorkerService.findById(Integer.parseInt(report .getUid()));
			Operation operation = operationService.findById(Integer.parseInt(report .getOid()));
			if (medicalWorker != null) {
				report.setName(medicalWorker.getName());
				report.setLocation(operation.getLocation());
			}
		}
		result.setCode(0);
		result.setMsg("SUCCESS");
		result.setCount(data.size());
		result.setData(data);
		return result;
	}

	@GetMapping("/now")
	public Result now(int limit,String location) {
		Result result = new Result();
		List<Report> data;
		String oid = operationService.findNowByLocation(location).getId().toString();
		data = reportService.findNewsByOid(limit,oid);
		for (Report report : data) {
			MedicalWorker medicalWorker = medicalWorkerService.findById(Integer.parseInt(report .getUid()));
			Operation operation = operationService.findById(Integer.parseInt(report .getOid()));
			if (medicalWorker != null) {
				report.setName(medicalWorker.getName());
				report.setLocation(operation.getLocation());
			}
		}
		result.setCode(0);
		result.setMsg("SUCCESS");
		result.setCount(data.size());
		result.setData(data);
		return result;
	}
}
