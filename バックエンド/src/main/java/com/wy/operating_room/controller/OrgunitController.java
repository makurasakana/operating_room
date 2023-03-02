package com.wy.operating_room.controller;

import com.wy.operating_room.core.Result;
import com.wy.operating_room.repository.OrgunitRepository;
import com.wy.operating_room.repository.PersonRepository;
import com.wy.operating_room.entity.Orgunit;
import com.wy.operating_room.entity.Person;
import com.wy.operating_room.service.OrgunitService;
import com.wy.operating_room.util.StringUtil;
import com.wy.operating_room.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/orgunit")
public class OrgunitController {

    @Autowired
    OrgunitService orgunitService;
    @Autowired
    OrgunitRepository orgunitRepository;
    @Autowired
    PersonRepository personRepository;


    @GetMapping("/list")
    public Result list() {
        return Result.ofsuccess(orgunitRepository.findAll());
    }

    @GetMapping("/detail/{id}")
    public Result detail(@PathVariable String id) {

        Orgunit orgunit = new Orgunit();
        List<Person> personList = personRepository.findAllByOrgunit(id);

        Optional<Orgunit> optional = orgunitRepository.findById(id);
        if (optional.isPresent()) {
            orgunit = optional.get();
        }

        Map map = new HashMap();
        map.put("orgunit", orgunit);
        map.put("personList", personList);

        return Result.ofsuccess(map);
    }

    @PostMapping("/create")
    public Result create(@RequestParam(required = false) String ids, @RequestBody Orgunit orgunit) {
        orgunit.setId(UuidUtil.randomUUID());
        orgunitService.initIdpathByParent(orgunit);
        orgunitRepository.save(orgunit);

        if (StringUtil.isNotBlank(ids)) {
            String[] idarr = ids.split(",");
            for (String id : idarr) {
                Optional<Person> optionalPerson = personRepository.findById(id);
                if (optionalPerson.isPresent()) {
                    Person person = optionalPerson.get();
                    person.setOrgunit(orgunit.getId());
                    personRepository.save(person);
                }
            }
        }
        return Result.ofsuccess(orgunit);
    }

    @PostMapping("/edit")
    public Result edit(@RequestParam(required = false) String ids, @RequestBody Orgunit orgunit) {
        Optional<Orgunit> _orgunit = orgunitRepository.findById(orgunit.getId());
        if (_orgunit.isPresent()) {
            orgunitService.initIdpathByParent(orgunit);
            orgunitRepository.save(orgunit);

            List<Person> personList = personRepository.findAllByOrgunit(orgunit.getId());
            personList.forEach(item -> {
                item.setOrgunit(null);
                personRepository.save(item);
            });

            if (StringUtil.isNotBlank(ids)) {
                String[] idarr = ids.split(",");
                for (String id : idarr) {
                    Optional<Person> optionalPerson = personRepository.findById(id);
                    if (optionalPerson.isPresent()) {
                        Person person = optionalPerson.get();
                        person.setOrgunit(orgunit.getId());
                        personRepository.save(person);
                    }
                }
            }

            return Result.ofsuccess(true);
        }
        return Result.ofsuccess(false);
    }

    @GetMapping("/delete/{id}")
    public Result delete(@PathVariable String id) {
        orgunitRepository.deleteByIdpathLike("%" + id + "%");
        return Result.ofsuccess(true);
    }

}
