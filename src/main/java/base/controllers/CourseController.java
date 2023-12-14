package base.controllers;

import base.daos.CourseDao;
import base.models.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CourseController {

    @Autowired
    CourseDao courseDao ;

    @GetMapping("/courseReg")
    public String getCourseRegForm(Model model){
        Course course = new Course();
        course.setId(courseDao.getLatestCourseId());
        model.addAttribute("course",course);
        return "course/course_reg";
    }
    @PostMapping("/courseReg")
    public String courseRegistration(@ModelAttribute ("course") Course course ){
        int result = courseDao.createCourse(course);
        return "redirect:/courseView";
    }

    @GetMapping("/courseView")
    public String showAllCourses(Model model){
        model.addAttribute("courses" , courseDao.getAllCourses());
        return "course/course_view";
    }



}
