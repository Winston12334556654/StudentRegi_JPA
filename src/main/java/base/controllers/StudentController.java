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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @PostMapping("/studReg")
    public String registerStudent(@ModelAttribute("student")Student student ,HttpServletRequest request ){

        //initializing course to assign later..
        List<Course> courses = new ArrayList<>();

        //String list of course id that come from studentReg jsp
        List<String> courseIds = student.getCourseIds();
        for (String courseId : courseIds){
            Course course = cDao.findCourseById(courseId);          //find the course that concern with single course id
            courses.add(course);                //add single course to courses list
        }
        student.setCourses(courses);         //add courses list to student object back

        // to save image
        MultipartFile file = student.getFile();
        if (file != null && !file.isEmpty()) {
            String image = saveImage(file, request);
            student.setPhoto(image); // save image to student object
        }
        int result =  sDao.createStudent(student);
        return "student/stud_view";
    }

    @GetMapping("/studView")
    public String displayStudents(Model model){
        List<Student> students = sDao.getAllStudent();
        model.addAttribute("student",new Student());
        model.addAttribute("students",students);
        return "student/stud_view";
    }

    @GetMapping("/studDetail")
    public String getStudentDetailForm(@RequestParam("id")String id , Model model){
        Student student = sDao.findStudentById(id);
        System.out.println(student.getCourses());
        model.addAttribute("courses",cDao.getAllCourses());
        System.out.println(cDao.getAllCourses());
        model.addAttribute("student",student);
        return "student/stud_detail";
    }
    @PostMapping("/studUpdate")
    public String updateStudent(@ModelAttribute("student")Student student , HttpServletRequest request){
        List<Course> courses = new ArrayList<>();
        List<String> courseIds = student.getCourseIds();
        for (String courseId : courseIds){

            //find the course that concern with single course id
            Course course = cDao.findCourseById(courseId);
            //add single course to courses list
            courses.add(course);
        }
        //add courses list to student object back
        student.setCourses(courses);

        // to save image
        MultipartFile file = student.getFile();
        if (file != null && !file.isEmpty()) {
            String image = saveImage(file, request);
            student.setPhoto(image); // save image to student object
        }

        int result = sDao.updateStudent(student);
        return "redirect:/studView";
    }

    @GetMapping("/studDelete")
    public String studentDelete(@RequestParam("id")String id ){
        int result =  sDao.deleteStudent(id);
        return "redirect:/studView";
    }


    @PostMapping("/studView/search")
    public String searchingStudent(@ModelAttribute("student")Student student ,
                                   Model model,
                                   @RequestParam("course") String course){
        System.out.println(student);

        List<Student> students;
        if(!student.getId().isEmpty()){
            students = sDao.searchStudentById(student.getId());
            model.addAttribute("students",students);
            return "student/stud_view";
        }
        else if(!student.getName().isEmpty()){
            students = sDao.searchStudentsByName(student.getName());
            model.addAttribute("students",students);
            return "student/stud_view";
        }
        else if (!course.isEmpty()){
            students = sDao.searchStudentsByCourse(course);
            model.addAttribute("students",students);
            return "student/stud_view";
        }
        else {
            return "student/stud_view";
        }
    }

    //image save extract method
    private String saveImage(MultipartFile file, HttpServletRequest request)  {
        if (file != null && !file.isEmpty()) {
            String originalFileName = file.getOriginalFilename();
            assert originalFileName != null;
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));

            String image = System.currentTimeMillis() + fileExtension ;

            String rootDirectory = request.getSession().getServletContext().getRealPath("/");
            Path path = Paths.get(rootDirectory + "/WEB-INF/assets/imgs/" + image);
            try {
                file.transferTo(new File(path.toString()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return image ;
        }
        else {
            return null;
        }
    }



}
