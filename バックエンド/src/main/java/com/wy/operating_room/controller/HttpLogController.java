package com.wy.operating_room.controller;

import com.wy.operating_room.core.Result;
import com.wy.operating_room.repository.HttpLogRepository;
import com.wy.operating_room.service.HttpLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;


@RestController
@RequestMapping("/httpLog")
public class HttpLogController {

    @Autowired
    HttpLogRepository httpLogRepository;
    @Autowired
    HttpLogService httpLogService;


    @GetMapping("num")
    public Result num() {
        return Result.ofsuccess(httpLogRepository.count());
    }

    @PostMapping("/find")
    public Result find(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                       @RequestParam(value = "pageSize", required = false) Integer pageSize,
                       @RequestBody LinkedHashMap queryParam) {
        return Result.ofsuccess(httpLogService.search(queryParam, pageNumber, pageSize));
    }

}
