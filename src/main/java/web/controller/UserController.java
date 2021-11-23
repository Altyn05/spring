package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class UserController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/")
    public String hello(Model model) {
        model.addAttribute("users", "hello");
        return "hello";
    }

    @GetMapping(value = "/user")
    public String user(Model model, Principal principal) {
        model.addAttribute("user", userService.getUserByName(principal.getName()));
        return "user";
    }

    @GetMapping(value = "/admin")
    public String showAll(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "all";
    }

    @GetMapping(value = "/admin/add")
    public String add(Model model) {
        model.addAttribute("user", new User());
        return "add";
    }

    @PostMapping("/admin/create")
    public String actionAdd(@ModelAttribute("user") User user,
                            @RequestParam("checkBox") String[] checkBox) {
        Set<Role> allRoles = new HashSet<>();
        for (String role : checkBox) {
            allRoles.add(roleService.getRoleByName(role));
        }
        user.setRoles(allRoles);
        userService.add(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/admin/edit")
    public String edit(@RequestParam(value = "id") long id, Model model) {
        if (id < 0) {
            return "redirect:/admin";
        }
        User user = userService.getUserById(id);
        if (user == null) {
            return "redirect:/admin";
        }
        model.addAttribute("user", user);

        return "edit";
    }

    @PutMapping("admin/{id}")
    public String actionEdit(@ModelAttribute("user") User user, @RequestParam("checkBox") String[] checkBox) {
        Set<Role> set = new HashSet<>();
        for (String role : checkBox) {
            set.add(roleService.getRoleByName(role));
        }
        user.setRoles(set);
        userService.upDateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping(value = "delete/{id}")
    public String actionDelete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping(value = "hello")
    public String printWelcome(ModelMap model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello!");
        messages.add("I'm Spring MVC-SECURITY application");
        messages.add("5.2.0 version by sep'19 ");
        model.addAttribute("messages", messages);
        return "hello";
    }


}