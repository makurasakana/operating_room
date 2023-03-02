package com.wy.operating_room.service;

import com.wy.operating_room.repository.AccountRepository;
import com.wy.operating_room.entity.Account;
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
public class AccountService implements IService<Account> {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public Page<Account> search(LinkedHashMap queryParam, int pageNumber, int pageSize) {

        Pageable pageable = ofPageable(queryParam, pageNumber, pageSize);

        Page<Account> page = accountRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicatesList = new ArrayList<>();

            if (queryParam != null) {

                if (StringUtil.isNotBlank(queryParam.get("username"))) {
                    predicatesList.add(criteriaBuilder.and(criteriaBuilder.like(root.get("username"), "%" + queryParam.get("username") + "%")));
                }
            }

            return criteriaBuilder.and(predicatesList.toArray(new Predicate[predicatesList.size()]));
        }, pageable);

        return page;
    }

}
