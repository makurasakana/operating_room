package com.wy.operating_room.service;

import org.springframework.stereotype.Service;
import com.wy.operating_room.entity.Result;
import com.wy.operating_room.entity.ResultData;
import com.wy.operating_room.entity.Operation;
import com.wy.operating_room.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class OperationService {

	@Autowired
	private OperationRepository operationRepository;

	
	public Operation findById(int id){
		Operation one = operationRepository.findById(id).orElse(null);
		return one;
	}

	
	public ResponseEntity deleteOne(int id){
		operationRepository.deleteById(id);
		return ResultData.success();
	}

	
	public Result saveOne(Operation entity){
		Result result = new Result();
		Operation one = operationRepository.save(entity);
		result.setData(one);
	return result;
	}

	
	public List<Operation> findAll() {return operationRepository.findAll(); }

    
    public List<Operation> findByPid(String pid) {return operationRepository.findByPid(pid); }

	public List<Operation> findMyAll(String anesthesiologist) {return operationRepository.findMyAll(anesthesiologist); }

	public List<Operation> findPage(int page, int limit){
		List<Operation> OperationList = operationRepository.findPage(page,limit);
		return OperationList;
	}
    
    public List<Operation> findPageByPid(String pid, int page, int limit){
        List<Operation> OperationList = operationRepository.findPageByPid(pid, page,limit);
        return OperationList;
    }

	public List<Operation> findMyPage(String anesthesiologist, int page, int limit){
		List<Operation> OperationList = operationRepository.findMyPage(anesthesiologist, page,limit);
		return OperationList;
	}

	public Operation findCurrent(String nickname){
		Operation operation = operationRepository.current(nickname);
		return operation;
	}

	public List<Operation> getOnOperation(){
		List<Operation> operations = operationRepository.getOnOperation();
		return operations;
	}

	public List<Operation> getThis(String id){
		List<Operation> operations = operationRepository.getThis(Integer.parseInt(id));
		return operations;
	}
}
