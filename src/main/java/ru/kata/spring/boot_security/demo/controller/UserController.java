package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RolesRepository;
import ru.kata.spring.boot_security.demo.service.UserDService;
import ru.kata.spring.boot_security.demo.service.UserService;


import java.security.Principal;
import java.util.List;

@RestController
@Controller
@RequestMapping("/")
public class UserController {
    private UserService userService;

    @Autowired
    private UserDService userDService;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    public void setUserDService(UserDService userDService) {
        this.userDService = userDService;
    }

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/users/new")
    public ModelAndView newUser() {
        User user = new User();
        ModelAndView mav = new ModelAndView("user_form");
        mav.addObject("user", user);
        List<Role> roles = (List<Role>) rolesRepository.findAll();
        mav.addObject("allRoles", roles);
        return mav;
    }

    @GetMapping("/admin/users/edit/{id}")
    public ModelAndView editUser(@PathVariable(name = "id") Integer id) {
        User user = userService.getUser(id);
        ModelAndView mav = new ModelAndView("user_form");
        mav.addObject("user", user);
        List<Role> roles = (List<Role>) rolesRepository.findAll();
        mav.addObject("allRoles", roles);
        return mav;
    }

    @GetMapping("/admin/showUsers")
    public String showUsers(Model model) {
        List<User> usersList = userService.getUserList();
        model.addAttribute("usersList", usersList);
        return "users";
    }

    @GetMapping("/user")
    public String pageForAuthenticatedUsers(Principal principal) {
        User user = userDService.findByUsername(principal.getName());
        return "You are successfully authorized!/n" + "Username: " + user.getUsername() + "/nPassword: " + user.getPassword() + "/nEmail: " + user.getEmail();
    }

    @GetMapping("/admin/userInfo")
    public String showAddUserPage(Model model) {
        model.addAttribute("user", new User());
        return "addUser";
    }


    @GetMapping("/admin/updateUserForm")
    public String updateUserForm(@RequestParam("id") long id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "addUser";
    }

    @GetMapping("/admin/deleteUser")
    public String deleteUser(@RequestParam("id") long id) {
        userService.deleteUser(id);
        return "redirect:/";
    }
}
