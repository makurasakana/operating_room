package com.wy.operating_room.controller;

import com.wy.operating_room.core.Result;
import com.wy.operating_room.repository.ExceptioRepository;
import com.wy.operating_room.service.ExceptioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;


@RestController
@RequestMapping("/exceptio")
public class ExceptioController {

    @Autowired
    ExceptioRepository exceptioRepository;
    @Autowired
    ExceptioService exceptioService;


    @GetMapping("num")
    public Result num() {
        return Result.ofsuccess(exceptioRepository.count());
    }

    @PostMapping("/find")
    public Result find(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                       @RequestParam(value = "pageSize", required = false) Integer pageSize,
                       @RequestBody LinkedHashMap queryParam) {
        return Result.ofsuccess(exceptioService.search(queryParam, pageNumber, pageSize));
    }

}
