package base.controllers;

import base.daos.StudentDao;
import base.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StudentController {

    @Autowired
    StudentDao sDao ;

    @GetMapping("/studReg")
    public String getStudentRegForm(Model model){
        model.addAttribute("student",new Student());
        return "student/stud_reg";
    }
    @PostMapping("studReg")
    public String registerStudent(@ModelAttribute("student")Student student){
        int result =  sDao.createStudent(student);
        return "student/stud_view";
    }



}
