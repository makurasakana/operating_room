package com.wy.operating_room.controller;

import com.wy.operating_room.entity.Operation;
import com.wy.operating_room.entity.Patient;
import com.wy.operating_room.entity.Report;
import com.wy.operating_room.entity.Status;
import com.wy.operating_room.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Date;

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


    @RequestMapping("/")
    public String index(HttpSession session) {
//        Operation operation = operationService.findCurrent(session.getAttribute("nickname").toString());
        Operation operation = operationService.findCurrent("张三");
        Patient patient = patientService.findById(Integer.parseInt(operation.getPid()));
        operation.setPname(patient.getName());
        session.setAttribute("operation",operation);
        return "index";
    }

    @RequestMapping("/welcome")
    public String welcome(HttpSession session, Model model) {
        model.addAttribute("operation",session.getAttribute("operation"));
        return "welcome";
    }


    @RequestMapping("/my-operation")
    public String myOperation(HttpSession session, Model model) {
//        if (session.getAttribute("operation")==null)
//            return "404";
//        else {
            model.addAttribute("operation",session.getAttribute("operation"));
            return "my-operation";
//        }
    }

    @RequestMapping("/report/show")
    public String myReport() {
        return "my-report";
    }
    @RequestMapping("/status/show")
    public String myStatus() {
        return "my-status";
    }
    @RequestMapping("/anesthesia")
    public String anesthesia() {
//        return "anesthesia";
        return "404";
    }

    @RequestMapping("/anesthesia/show")
    public String showAnesthesia() {
        return "show-anesthesia";
    }
    @RequestMapping("/my-operation/medical-worker")
    public String showMedicalWorker(String name, Model model) {
        model.addAttribute("medicalWorker", medicalWorkerService.findByName(name));
        return "show-medical-worker";
    }
    @RequestMapping("/my-operation/patient")
    public String showPatient(String id, Model model) {
        model.addAttribute("patient",patientService.findById(Integer.parseInt(id)));
        return "show-patient";
    }

    @GetMapping("/report/add")
    public String repOperation(HttpSession session, Model model) {
        Report report = new Report();
//        report.setUid(session.getAttribute("id").toString());
        report.setUid("1");
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        report.setTime(df.format(new Date()));
        report.setTime(new Date());
        Operation operation = (Operation) session.getAttribute("operation");
        report.setOid(operation.getId().toString());
        model.addAttribute("report",report);
        return "report";
    }

    @GetMapping("/report/edit")
    public String modRepOperation(Integer id, HttpSession session, Model model) {
        Report report = reportService.findById(id);
        report.setTime(new Date());
        model.addAttribute("report",report);
        return "report";
    }

    @GetMapping("/status/add")
    public String repPatient(HttpSession session, Model model) {
        Status status = new Status();
//        status.setUid(session.getAttribute("id").toString());
        Status lastOne = statusService.findLastOne();
        BeanUtils.copyProperties(lastOne,status);
        status.setId(lastOne.getId()+1);
        status.setUid("1");
        status.setTime(new Date());
        Operation operation = (Operation) session.getAttribute("operation");
        status.setOid(operation.getId().toString());
        status.setPid(operation.getPid());
        model.addAttribute("status",status);
        return "status";
    }

    @GetMapping("/status/edit")
    public String modRepPatient(Integer id, HttpSession session, Model model) {
        Status status = statusService.findById(id);
        status.setTime(new Date());
        model.addAttribute("status",status);
        return "status";
    }
}
