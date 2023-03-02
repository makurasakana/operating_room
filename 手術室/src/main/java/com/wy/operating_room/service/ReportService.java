package com.wy.operating_room.service;

import com.wy.operating_room.repository.OperationRepository;
import org.springframework.stereotype.Service;
import com.wy.operating_room.entity.Result;
import com.wy.operating_room.entity.ResultData;
import com.wy.operating_room.entity.Report;
import com.wy.operating_room.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.util.List;

@Service
public class ReportService {

	@Autowired
	private ReportRepository reportRepository ;
	@Autowired
	private OperationRepository operationRepository ;
	
	public Report findById(int id) {
		Report one = reportRepository .findById(id).orElse(null);
		return one;
	}

	
	public ResponseEntity deleteOne(int id) {
//		reportRepository.deleteById(id);
		reportRepository.wasteById(id);
		return ResultData.success();
	}

	
	public Result saveOne(Report entity) {
		Result result = new Result();
		Report one = reportRepository .save(entity);
		if (entity.getType().equals("流程"))
		{
			entity.setState(entity.getDetail());
			operationRepository.saveState(entity.getState(),Integer.parseInt(entity.getOid()));
			operationRepository.saveEndTime(entity.getTime(),Integer.parseInt(entity.getOid()));
		}
		result.setData(one);
		return result;
	}

	
	public List<Report> findAll() {
		return reportRepository .findAll();
	}

	
	public List<Report> findByUid(String uid) {
		return reportRepository .findByUid(uid);
	}

	
	public List<Report> findByOid(String oid) {
		return reportRepository .findByOid(oid);
	}


	
	public List<Report> findPage(int page, int limit) {
		List<Report> ReportList = reportRepository .findPage(page, limit);
		return ReportList;
	}

	
	public List<Report> findPageByUid(String uid, int page, int limit) {
		List<Report> ReportList = reportRepository .findPageByUid(uid, page, limit);
		return ReportList;
	}

	
	public List<Report> findPageByOid(String oid, int page, int limit) {
		List<Report> ReportList = reportRepository .findPageByOid(oid, page, limit);
		return ReportList;
	}

	public List<Report> findNews(int limit) {
		List<Report> ReportList = reportRepository .findNews(limit);
		return ReportList;
	}
}
