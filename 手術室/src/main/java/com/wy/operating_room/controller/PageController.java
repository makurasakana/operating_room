package com.wy.operating_room.controller;

import com.wy.operating_room.entity.*;
import com.wy.operating_room.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;

import static com.wy.operating_room.util.Md5Util.StringToMd5;

@Controller
public class PageController {

    @Autowired
    OperationService operationService;
    @Autowired
    PatientService patientService;
    @Autowired
    ReportService reportService;
    @Autowired
    StatusService statusService;
    @Autowired
    MedicalWorkerService medicalWorkerService;
    @Autowired
    AccountService accountService;

    @RequestMapping("/")
    public String index(HttpSession session) {
        if(session.getAttribute("orgunit")==null)
            return "redirect:/login";
        else {
//        Operation operation = operationService.findCurrent(session.getAttribute("nickname").toString());
            Operation operation = operationService.findCurrent("张三");
            Patient patient = patientService.findById(Integer.parseInt(operation.getPid()));
            operation.setPname(patient.getName());
            operation.setDept(patient.getDept());
            operation.setBed(patient.getBed());
            session.setAttribute("operation", operation);
            return "index";
        }
    }

    @RequestMapping("/welcome")
    public String welcome(HttpSession session, Model model) {
        model.addAttribute("operation", session.getAttribute("operation"));
        model.addAttribute("user", session.getAttribute("orgunit"));
        return "welcome";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/toLogin")
    public String toLogin(Model model, HttpSession session, @RequestParam("username") String username, @RequestParam("password") String password) {
        Account account = accountService.findByUsername(username);
        String msg = null;
        if (account == null) {
            msg = "用户名不存在，请重新输入";
            model.addAttribute("msg", msg);
            return "login";
        } else {
            String orgunit = accountService.findOrg(account.getOrgunit());
            String pwd = account.getPassword();
            password = StringToMd5(password);
            System.out.println(orgunit);
            if (pwd != null && pwd.equals(password)) {
                if (orgunit.equals("巡回护士") || orgunit.equals("麻醉医师")) {
                    session.setAttribute("username", account.getUsername());
                    session.setAttribute("id", account.getId());
                    session.setAttribute("orgunit", orgunit);
                    return "redirect:/";
                } else {
                    msg = "非巡回护士或麻醉医师用户";
                    model.addAttribute("msg", msg);
                    return "login";
                }
            } else {
                msg = "密码错误，请重新输入";
                model.addAttribute("msg", msg);
                return "login";
            }
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.setAttribute("username", null);
        session.setAttribute("id", null);
        session.setAttribute("orgunit", null);
        return "redirect:/login";
    }

    @RequestMapping("/my-operation")
    public String myOperation(HttpSession session, Model model) {
        if (session.getAttribute("orgunit").equals("巡回护士")) {
            model.addAttribute("operation", session.getAttribute("operation"));
            return "my-operation";
        } else
            return "404";
    }

    @RequestMapping("/report/show")
    public String myReport(HttpSession session) {
        if (session.getAttribute("orgunit").equals("巡回护士"))
            return "my-report";
        else
            return "404";
    }

    @RequestMapping("/status/show")
    public String myStatus(HttpSession session) {
        if (session.getAttribute("orgunit").equals("巡回护士"))
            return "my-status";
        else
            return "404";
    }

    @RequestMapping("/anesthesia")
    public String anesthesia(HttpSession session) {
        if (session.getAttribute("orgunit").equals("麻醉医师"))
            return "anesthesia";
        else
            return "404";
    }

    @RequestMapping("/anesthesia/show")
    public String showAnesthesia(String oid, Model model) {
        model.addAttribute("oid", oid);
        return "show-anesthesia";
    }

    @RequestMapping("/my-operation/medical-worker")
    public String showMedicalWorker(String name, Model model) {
        model.addAttribute("medicalWorker", medicalWorkerService.findByName(name));
        return "show-medical-worker";
    }

    @RequestMapping("/my-operation/patient")
    public String showPatient(String id, Model model) {
        model.addAttribute("patient", patientService.findById(Integer.parseInt(id)));
        return "show-patient";
    }

    @GetMapping("/report/add")
    public String repOperation(HttpSession session, Model model) {
        Report report = new Report();
//        report.setUid(session.getAttribute("id").toString());
        report.setUid("1");
        report.setTime(new Date());
        Operation operation = (Operation) session.getAttribute("operation");
        report.setOid(operation.getId().toString());
        model.addAttribute("report", report);
        return "report";
    }

    @GetMapping("/report/edit")
    public String modRepOperation(Integer id, HttpSession session, Model model) {
        Report report = reportService.findById(id);
        report.setTime(new Date());
        model.addAttribute("report", report);
        return "report";
    }

    @GetMapping("/status/add")
    public String repPatient(HttpSession session, Model model) {
        Status status = new Status();
//        status.setUid(session.getAttribute("id").toString());
        Status lastOne = statusService.findLastOne();
        BeanUtils.copyProperties(lastOne, status);
        status.setId(lastOne.getId() + 1);
        status.setUid("1");
        status.setTime(new Date());
        Operation operation = (Operation) session.getAttribute("operation");
        status.setOid(operation.getId().toString());
        status.setPid(operation.getPid());
        model.addAttribute("status", status);
        return "status";
    }

    @GetMapping("/status/edit")
    public String modRepPatient(Integer id, HttpSession session, Model model) {
        Status status = statusService.findById(id);
        status.setTime(new Date());
        model.addAttribute("status", status);
        return "status";
    }
}
