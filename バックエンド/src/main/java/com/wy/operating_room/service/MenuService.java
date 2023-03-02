package com.wy.operating_room.service;

import com.wy.operating_room.repository.MenuRepository;
import com.wy.operating_room.entity.Menu;
import com.wy.operating_room.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class MenuService {

    @Autowired
    MenuRepository menuRepository;


    public void initIdpathByParent(Menu menu) {

        if (StringUtil.isNotBlank(menu.getPid())) {
            Optional<Menu> optional = menuRepository.findById(menu.getPid());
            if (optional.isPresent()) {
                menu.setIdpath(optional.get().getIdpath() + "," + menu.getId());
            } else {
                menu.setIdpath(menu.getId());
            }
        } else {
            menu.setIdpath(menu.getId());
        }
    }

}
