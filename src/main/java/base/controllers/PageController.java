package base.controllers;

import base.daos.UserDao;
import base.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
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
    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, Model model, HttpServletRequest request, RedirectAttributes red) {

        HttpSession session = request.getSession();
        String email = user.getEmail();

        User userByEmail = new User();
        userByEmail = userDao.getUserByEmail(email);
        System.out.println("user" + userByEmail);
        if (userByEmail != null) {

            if (user.getPassword().equals(userByEmail.getPassword())) {
                    session.setAttribute("name",userByEmail.getName());
                    session.setAttribute("id",userByEmail.getId());
            } else {
                model.addAttribute("msg", "passwordIncorrect");
                return "login";
            }
            model.addAttribute("msg", "Successful Login");
            return "menu";

        }
        red.addFlashAttribute("msg", "User not found! You have to registered First!");
        return "redirect:/";
    }


    @GetMapping("/logout")
    public String logout(Model model, HttpSession session){
        session.invalidate();
        return "redirect:/";
    }


}
