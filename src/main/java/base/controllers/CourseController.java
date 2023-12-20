package base.controllers;

import base.daos.CourseDao;
import base.models.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String courseRegistration(@ModelAttribute ("course") Course course , Model model ){
        String name=course.getName();
        Course course1=courseDao.getCourseByName(name);
        if(course1 !=null){
            model.addAttribute("error","Already exists");
            return "course/course_reg";
        }
        int result = courseDao.createCourse(course);
        return "redirect:/courseView";
    }

    @GetMapping("/courseView")
    public String showAllCourses(Model model){
        model.addAttribute("courses" , courseDao.getAllCourses());
        return "course/course_view";
    }

    @GetMapping("/courseUpdate")
    public String getCourseEditForm(@RequestParam("id")String id , Model model){
        Course  course = courseDao.findCourseById(id);
        model.addAttribute("course",course);
        return "course/course_detail";
    }
    @PostMapping("/courseUpdate")
    public String updateCourse(@ModelAttribute("course")Course course ){
        int result = courseDao.updateCourse(course);
        return "redirect:/courseView";
    }

    @GetMapping("/courseDelete")
    public String deletingUser(@RequestParam("id") String id){
        int resutl = courseDao.deleteCourse(id);
        return "redirect:/courseView";
    }



}
