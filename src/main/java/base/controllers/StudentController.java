package base.controllers;

import base.daos.CourseDao;
import base.daos.StudentDao;
import base.models.Course;
import base.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentController {

    @Autowired
    StudentDao sDao ;

    @Autowired
    CourseDao cDao ;

    @GetMapping("/studReg")
    public String getStudentRegForm(Model model){
        model.addAttribute("courses", cDao.getAllCourses());

        Student student = new Student();
        student.setId(sDao.getLatestStudentId());
        model.addAttribute("student",student);
        return "student/stud_reg";
    }
    @PostMapping("studReg")
    public String registerStudent(@ModelAttribute("student")Student student ){

        //initializing course to assign later..
        List<Course> courses = new ArrayList<>();

        //String list of course id that come from studentReg jsp
        List<String> courseIds = student.getCourseIds();

        for (String courseId : courseIds){

            //find the course that concern with single course id
            Course course = cDao.findCourseById(courseId);

            //add single course to courses list
            courses.add(course);
        }

        //add courses list to student object back
        student.setCourses(courses);

        int result =  sDao.createStudent(student);
        return "student/stud_view";
    }



}
