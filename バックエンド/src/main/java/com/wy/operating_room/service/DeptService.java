package com.wy.operating_room.service;

import com.wy.operating_room.entity.Dept;
import com.wy.operating_room.entity.Result;
import com.wy.operating_room.entity.ResultData;
import com.wy.operating_room.repository.DeptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptService {

	@Autowired
	private DeptRepository rep;

	
	public Dept findById(int id){
		Dept one = rep.findById(id).orElse(null);;
		return one;
	}

	
	public ResponseEntity deleteOne(int id){
		rep.deleteById(id);
		return ResultData.success();
	}

	
	public Result saveOne(Dept entity){
		Result result = new Result();
		Dept one = rep.save(entity);
		result.setData(one);
	return result;
	}

	
	public List<Dept> findAll() {return rep.findAll(); }

	
	public List<Dept> findPage(int page, int limit){
		List<Dept> DeptList = rep.findPage(page,limit);
		return DeptList;
	}
}
