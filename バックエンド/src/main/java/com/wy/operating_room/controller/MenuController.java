package com.wy.operating_room.controller;

import com.wy.operating_room.core.Result;
import com.wy.operating_room.helper.SqlHelper;
import com.wy.operating_room.repository.MenuRepository;
import com.wy.operating_room.repository.PermissionRepository;
import com.wy.operating_room.repository.PersonRepository;
import com.wy.operating_room.entity.Menu;
import com.wy.operating_room.entity.Person;
import com.wy.operating_room.service.IdentityService;
import com.wy.operating_room.service.MenuService;
import com.wy.operating_room.util.RequestUtil;
import com.wy.operating_room.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    MenuService menuService;
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    SqlHelper sqlHelper;
    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    IdentityService identityService;
    @Autowired
    HttpServletRequest servletRequest;


    @GetMapping("/get")
    public Result get() {
        String token = RequestUtil.getToken(servletRequest);
        Person person = identityService.getPersonByToken(token);
        return Result.ofsuccess(person != null ? menuRepository.getByPerson(person.getId()) : new ArrayList<>());
    }

    @GetMapping("/getByPerson/{id}")
    public Result getByPerson(@PathVariable String id) {
        return Result.ofsuccess(menuRepository.getByPerson(id));
    }

    @GetMapping("/getByOrgunit/{id}")
    public Result getByOrgunit(@PathVariable String id) {
        return Result.ofsuccess(menuRepository.getByOrgunit(id));
    }


    @GetMapping("/configData")
    public Result configData() {

        Map map = new HashMap();

        String sql = String.format("" +
                "SELECT id,title,pid,'bank' as icon,false as permission FROM sys_orgunit\n" +
                "UNION ALL\n" +
                "SELECT id,title,orgunit AS pid,'user' as icon,false as permission FROM sys_person");

        map.put("menus", menuRepository.findAllByOrderBySorttAsc());
        map.put("peoples", sqlHelper.query(sql));

        return Result.ofsuccess(map);
    }

    @GetMapping("/configDetail/{id}")
    public Result detail(@PathVariable String id) {

        Menu menu = new Menu();

        Optional<Menu> optional = menuRepository.findById(id);
        if (optional.isPresent()) {
            menu = optional.get();
        }

        Map map = new HashMap();
        map.put("menu", menu);
        map.put("permission", permissionRepository.getByResource(id));

        return Result.ofsuccess(map);
    }

    @PostMapping("/create")
    public Result create(@RequestBody Menu menu) {
        menu.setId(UuidUtil.randomUUID());
        menuService.initIdpathByParent(menu);
        menuRepository.save(menu);
        return Result.ofsuccess(menu);
    }

    @PostMapping("/edit")
    public Result edit(@RequestBody Menu menu) {
        Optional<Menu> optional = menuRepository.findById(menu.getId());
        if (optional.isPresent()) {
            menuService.initIdpathByParent(menu);
            menuRepository.save(menu);
            return Result.ofsuccess(true);
        }
        return Result.ofsuccess(false);
    }

    @GetMapping("/delete/{id}")
    public Result delete(@PathVariable String id) {
        menuRepository.deleteByIdpathLike("%" + id + "%");
        return Result.ofsuccess(true);
    }

}
