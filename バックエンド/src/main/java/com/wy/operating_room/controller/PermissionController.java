package com.wy.operating_room.controller;

import com.wy.operating_room.core.Result;
import com.wy.operating_room.repository.PermissionRepository;
import com.wy.operating_room.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    PermissionRepository permissionRepository;

    @GetMapping("/getByResource/{id}")
    public Result getByResource(@PathVariable String id) {
        return Result.ofsuccess(permissionRepository.getByResource(id));
    }

    @GetMapping("/allow/{resource}/{target}")
    public Result allow(@PathVariable String resource, @PathVariable String target) {
        Optional<Permission> optional = permissionRepository.findByResourceAndTarget(resource, target);
        if (!optional.isPresent()) {
            Permission permission = new Permission();
            permission.setResource(resource);
            permission.setTarget(target);
            permissionRepository.save(permission);
        }
        return Result.ofsuccess(true);
    }

    @GetMapping("/cancel/{resource}/{target}")
    public Result cancel(@PathVariable String resource, @PathVariable String target) {
        Optional<Permission> optional = permissionRepository.findByResourceAndTarget(resource, target);
        if (optional.isPresent()) {
            permissionRepository.deleteById(optional.get().getId());
        }
        return Result.ofsuccess(true);
    }
}
