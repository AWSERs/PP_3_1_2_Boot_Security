package ru.kata.spring.boot_security.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Controller
public class AdminController {

    private final UserServiceImpl userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminController(UserServiceImpl userService, UserRepository userRepository, RoleRepository roleRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public Set<Role> getRole(String[] roles){
        Set<Role> userRole = new HashSet<>();
        for (String role : roles){
            userRole.add(roleRepository.findByName(role));
        }

        return userRole;
    }

    @GetMapping("/admin")
    public String getAdminPage(){
        return "admin";
    }

    @GetMapping("/admin_b")
    public String getAdmin_bPage(Model model, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        model.addAttribute("userIn", user);
        model.addAttribute("users", userService.getAllUsers());
        return "admin_panel";
    }

    @GetMapping("/admin/users")
    public String getUsers(Model model){
        model.addAttribute("users", userService.getAllUsers());
        return "users-list";
    }

    @GetMapping("/admin_b/users")
    public String getUsers_bPage(Model model){
        model.addAttribute("users", userService.getAllUsers());
        return "admin_panel";
    }

    @GetMapping("/admin/user-create")
    public String createUserForm(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("role", new ArrayList<Role>());
        return "user-create";
    }

    @PostMapping("/admin/user-create")
    public String createUser(@ModelAttribute("user") User user, @RequestParam(value = "role") String[] roles){
        user.setRoles(getRole(roles));
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/user-update/{id}")
    public String updateUserById (@PathVariable ("id") int id, Model model){
        User user = userRepository.getById(id);
        model.addAttribute("user", user);
        return "user-update";
    }

    @PostMapping("admin/user-update")
    public String updateUser (@RequestParam(value = "role") String[] roles, @ModelAttribute("user") User user){
        user.setRoles(getRole(roles));
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("admin/user-delete/{id}")
    public String deleteUser(@PathVariable("id") int id){
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }






}
