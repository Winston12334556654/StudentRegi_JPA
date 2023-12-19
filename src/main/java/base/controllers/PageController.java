package base.controllers;

import base.daos.UserDao;
import base.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class PageController {


    @Autowired
    UserDao userDao;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("user" , new User());
        return "login";
    }

    //login mehtod

    @PostMapping("/login")
    public String loging(Model model, @ModelAttribute("user")User user, HttpSession session){


        //is there any one with this email and password
        boolean userResult= userDao.login(user);
        if (!userResult){

                model.addAttribute("msg", "User email or password incorrect");
                return "login";

//            return "redirect:/";
        }




        String name= userDao.getNameByEmail(user.getEmail());
        String role= userDao.getRoleByEmail(user.getEmail());
//        System.out.print("name   :  "+name );
//        System.out.print("role   :  "+role );
//         user.setName(name);
//         user.setRole(role);


        //set session name
        session.setAttribute("name", name);


        //set session role
        session.setAttribute("role", role);
        session.setAttribute("id", userDao.getUserIdByEmail(user.getEmail()));



        System.out.println("heeee     :   " + user.getName());

        model.addAttribute("users",userDao.getAllUsers());

//        model.addAttribute("user",new User());
        return "user/user_view";

    }

    @GetMapping("/logout")
    public String logout(Model model, HttpSession session){

        session.invalidate();
        return "redirect:/";
    }





}
