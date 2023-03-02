package com.wy.operating_room.service;

import com.wy.operating_room.repository.ExceptioRepository;
import com.wy.operating_room.entity.Exceptio;
import com.wy.operating_room.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


@Service
public class ExceptioService implements IService<Exceptio> {

    @Autowired
    ExceptioRepository exceptioRepository;

    @Override
    public Page search(LinkedHashMap queryParam, int pageNumber, int pageSize) {

        Pageable pageable = ofPageable(queryParam, pageNumber, pageSize);

        Page page = exceptioRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicatesList = new ArrayList<>();

            if (queryParam != null) {

                if (StringUtil.isNotBlank(queryParam.get("personid"))) {
                    predicatesList.add(criteriaBuilder.and(criteriaBuilder.like(root.get("personid"), "%" + queryParam.get("personid") + "%")));
                }

                if (StringUtil.isNotBlank(queryParam.get("url"))) {
                    predicatesList.add(criteriaBuilder.and(criteriaBuilder.like(root.get("url"), "%" + queryParam.get("url") + "%")));
                }

                if (StringUtil.isNotBlank(queryParam.get("method"))) {
                    predicatesList.add(criteriaBuilder.and(criteriaBuilder.like(root.get("method"), "%" + queryParam.get("method") + "%")));
                }

                // 大于或等于传入时间
                if (StringUtil.isNotBlank(queryParam.get("start"))) {
                    predicatesList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createtime").as(String.class), queryParam.get("start").toString()));
                }

                // 小于或等于传入时间
                if (StringUtil.isNotBlank(queryParam.get("end"))) {
                    predicatesList.add(criteriaBuilder.lessThanOrEqualTo(root.get("createtime").as(String.class), queryParam.get("end").toString()));
                }
            }

            return criteriaBuilder.and(predicatesList.toArray(new Predicate[predicatesList.size()]));
        }, pageable);

        return page;
    }
}
