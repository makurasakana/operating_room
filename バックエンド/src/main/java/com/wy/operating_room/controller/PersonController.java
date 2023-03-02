package com.wy.operating_room.controller;

import com.wy.operating_room.core.Audience;
import com.wy.operating_room.core.Result;
import com.wy.operating_room.entity.Account;
import com.wy.operating_room.repository.AccountRepository;
import com.wy.operating_room.repository.LoginRepository;
import com.wy.operating_room.repository.PersonRepository;
import com.wy.operating_room.entity.Login;
import com.wy.operating_room.entity.Person;
import com.wy.operating_room.service.AccountService;
import com.wy.operating_room.service.IdentityService;
import com.wy.operating_room.service.LoginService;
import com.wy.operating_room.service.PersonService;
import com.wy.operating_room.util.CreateTokenUtil;
import com.wy.operating_room.util.GsonUtil;
import com.wy.operating_room.util.RequestUtil;
import com.wy.operating_room.util.UuidUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    Audience audience;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    LoginRepository loginRepository;
    @Autowired
    LoginService loginService;
    @Autowired
    PersonService personService;
    @Autowired
    AccountService accountService;
    @Autowired
    IdentityService identityService;
    @Autowired
    HttpServletRequest servletRequest;


    @GetMapping("num")
    public Result num() {
        return Result.ofsuccess(personRepository.count());
    }

    @PostMapping("/account/login")
    public Result login(@RequestBody Map loginIdentity) {

        if (loginIdentity == null) {
            return Result.oflost("登陆信息为空！", 201);
        }

        Person person = identityService.identityCheck(loginIdentity);
        if (person != null) {
            //  生成token
            String accessToken = CreateTokenUtil.createJWT(person.getTitle(), audience.getClientId(), audience.getName(), audience.getExpiresSecond() * 1000, audience.getBase64Secret());
            // 将 token 保存在内存中，以后请求 API 时，从 request header 取出 token 进行判读【用户是否已登陆】
            identityService.login(accessToken, loginIdentity, person);

            // 添加登陆日志
            loginRepository.save(Login.of(person.getId(), accessToken, servletRequest));

            Map<String, Object> map = GsonUtil.objectToMap(person);
            map.put("token", accessToken);

            return Result.ofsuccess(map);
        }

        String username = null == loginIdentity.get("username") ? null : loginIdentity.get("username").toString();
        String password = null == loginIdentity.get("password") ? null : loginIdentity.get("password").toString();
        if (StringUtils.isEmpty(username)) {
            return Result.oflost("账号信息为空！", 201);
        }
        if (StringUtils.isEmpty(password)) {
            return Result.oflost("密码信息为空！", 201);
        }
        Optional<Person> _option = personRepository.findByUsername(username);
        if (!_option.isPresent()) {
            return Result.oflost("账号不存在！", 201);
        } else {
            return Result.oflost("密码错误！", 201);
        }
    }

    @GetMapping("/isLogin")
    public Result isLogin() {
        String token = RequestUtil.getToken(servletRequest);
        if (identityService.isLogin(token)) {
            return Result.ofsuccess(true);
        }
        return Result.oflost(null, 401);
    }

    @GetMapping("/logout")
    public Result logout() {
        String token = RequestUtil.getToken(servletRequest);
        if (StringUtils.isNotEmpty(token)) {

            identityService.logout(token);

            Optional<Login> optionalLogin = loginRepository.findByToken(token);
            if (optionalLogin.isPresent()) {
                Login login = optionalLogin.get();
                login.setLogouttime(LocalDateTime.now().toString());
                loginRepository.save(login);
            }
        }
        return Result.ofsuccess(null);
    }


    @PostMapping("/account/find")
    public Result findAccount(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                       @RequestParam(value = "pageSize", required = false) Integer pageSize,
                       @RequestBody LinkedHashMap queryParam) {
        return Result.ofsuccess(accountService.search(queryParam, pageNumber, pageSize));
    }


    @PostMapping("/find")
    public Result find(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                       @RequestParam(value = "pageSize", required = false) Integer pageSize,
                       @RequestBody LinkedHashMap queryParam) {
        return Result.ofsuccess(personService.search(queryParam, pageNumber, pageSize));
    }

    @PostMapping("/log")
    public Result Loginfind(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                            @RequestParam(value = "pageSize", required = false) Integer pageSize,
                            @RequestBody LinkedHashMap queryParam) {
        return Result.ofsuccess(loginService.search(queryParam, pageNumber, pageSize));
    }

    @PostMapping("/online")
    public Result findLogin() {
        return Result.ofsuccess(identityService.getAllPeople());
    }

    @GetMapping("/detail/{id}")
    public Result detail(@PathVariable String id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isPresent()) {
            return Result.ofsuccess(optionalPerson.get());
        }
        return Result.oflost("", 201);
    }

    @PostMapping("/create")
    public Result create(@RequestBody Person person) {
        person.setId(UuidUtil.randomUUID());
        return Result.ofsuccess(personRepository.save(person));
    }

    @PostMapping("/edit")
    public Result edit(@RequestBody Person person) {
        Optional<Person> _person = personRepository.findById(person.getId());
        if (_person.isPresent()) {
            personRepository.save(person);
            return Result.ofsuccess(true);
        }
        return Result.ofsuccess(false);
    }

    @GetMapping("/delete/{id}")
    public Result delete(@PathVariable String id) {
        personRepository.deleteById(id);
        return Result.ofsuccess(true);
    }

    @GetMapping("/account/detail/{id}")
    public Result accountDetail(@PathVariable String id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            return Result.ofsuccess(optionalAccount.get());
        }
        return Result.oflost("", 201);
    }

    @PostMapping("/account/create")
    public Result accountCreate(@RequestBody Account account) {
        account.setId(UuidUtil.randomUUID());
        return Result.ofsuccess(accountRepository.save(account));
    }

    @PostMapping("/account/edit")
    public Result accountEdit(@RequestBody Account account) {
        Optional<Account> _account = accountRepository.findById(account.getId());
        if (_account.isPresent()) {
            accountRepository.save(account);
            return Result.ofsuccess(true);
        }
        return Result.ofsuccess(false);
    }

    @GetMapping("/account/delete/{id}")
    public Result accountDelete(@PathVariable String id) {
        accountRepository.deleteById(id);
        return Result.ofsuccess(true);
    }
}
