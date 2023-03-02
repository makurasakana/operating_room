package com.wy.operating_room.service;

import com.wy.operating_room.repository.OrgunitRepository;
import com.wy.operating_room.entity.Orgunit;
import com.wy.operating_room.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class OrgunitService {

    @Autowired
    OrgunitRepository orgunitRepository;


    public void initIdpathByParent(Orgunit orgunit) {

        if (StringUtil.isNotBlank(orgunit.getPid())) {
            Optional<Orgunit> optional = orgunitRepository.findById(orgunit.getPid());
            if (optional.isPresent()) {
                orgunit.setIdpath(optional.get().getIdpath() + "," + orgunit.getId());
            } else {
                orgunit.setIdpath(orgunit.getId());
            }
        } else {
            orgunit.setIdpath(orgunit.getId());
        }
    }

}
