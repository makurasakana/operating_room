package com.wy.operating_room.service;

import com.wy.operating_room.entity.Account;
import com.wy.operating_room.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public Account findByUsername(String username){
        return accountRepository.findByUsername(username);
    }

    public String findOrg(String orgunit){
        return accountRepository.findOrg(orgunit);
    }
}
