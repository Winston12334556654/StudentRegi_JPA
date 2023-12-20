package base.controllers;

import base.daos.UserDao;
import base.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserDao userDao;

    @GetMapping("/userReg")
    public String getUserRegForm(Model model){
        User user = new User();
        user.setId(userDao.getLatestUserId());
        model.addAttribute("user",user);
        return "user/user_reg";
    }
    @PostMapping("/userReg")
    public String userRegForm(Model model, @ModelAttribute("user") User user , RedirectAttributes redirect , HttpSession session) {

        User userByEmail=userDao.getUserByEmail(user.getEmail());
        if(userByEmail !=null){
            model.addAttribute("msg","This email is already exists.Try another one");
            return "user/user_reg";
        }
        int result = userDao.createUser(user);
        if (result == 0) {
            return "user/user_reg";
        } else {
            String id = (String) session.getAttribute("id");
            if (!id.isEmpty()){
                return "redirect:/userView";
            }
            redirect.addFlashAttribute("msg", "Register Success...! Sign in Now . ");
            return "redirect:/";
        }
    }

    @GetMapping("/userView")
    public String userDisplay(Model model){
        model.addAttribute("users",userDao.getAllUsers());
        model.addAttribute("user",new User());
        return "user/user_view";
    }

    @GetMapping("/userDetail")
    public String getUserProfileForm(@RequestParam("id")String id , Model model){
        model.addAttribute("user",userDao.findUserById(id));
        return "user/user_detail";
    }
    @PostMapping("/userDetail")
    public String userProfileUpdate(@ModelAttribute("user")User user , Model model, HttpSession session){
        int result = userDao.updateUser(user);
        session.setAttribute("name",user.getName());
        return "redirect:/userView";
    }

    @GetMapping("/userDelete")
    public String userDelete(@RequestParam("id")String id , HttpSession session){
        int result =  userDao.deleteUser(id);
        String sid = (String) session.getAttribute("id");
        if (sid.equals(id)){
            return "redirect:/logout";
        }
        return "redirect:/userView";
    }

    @PostMapping("/userView/search")
    public String searchingUser(@ModelAttribute("user")User user , Model model){
        List<User> users;
        if(!user.getId().isEmpty()){
            users = userDao.searchUserById(user.getId());
            model.addAttribute("users",users);
            return "user/user_view";
        }
        else if(!user.getName().isEmpty()){
            users = userDao.searchUsersByName(user.getName());
            model.addAttribute("users",users);
            System.out.println("There:");
            return "user/user_view";
        }
        else {
            return "redirect:/userView";
        }
    }

}
