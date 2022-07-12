package ru.kata.spring.boot_security.demo.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;
import java.util.ArrayList;
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

    @RequestMapping(value = "/admin_b", method = RequestMethod.GET)
    public String getAdmin_bPage(Model model, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        model.addAttribute("userIn", user);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("newUser", new User());
        model.addAttribute("newRole", new ArrayList<Role>());

        return "admin_panel";
    }

    @RequestMapping(value = "/admin_b", method = RequestMethod.POST)
    public String postNewUser(@ModelAttribute("newUser") User newUser, @RequestParam(value = "newRole") String[] roles){
        newUser.setRoles(getRole(roles));
        userService.saveUser(newUser);
        return "redirect:/admin_b";
    }

    @RequestMapping(value = "/admin_b/edit", method = RequestMethod.POST)
    public String editUser(@ModelAttribute("userEdit") User userEdit, @RequestParam(value = "editRole") String[] roles){
        userEdit.setRoles(getRole(roles));
        userService.saveUser(userEdit);
        return "redirect:/admin_b";
    }

    @RequestMapping(value = "/admin_b/{id}", method = RequestMethod.GET)
    public String updateUserById (@PathVariable ("id") int id, Model model){
        User user = userRepository.getById(id);
        model.addAttribute("userEdit", user);
        return "redirect:/admin_b";
    }

    @PostMapping(path = "/admin/user-update")
    public String updateUser (@ModelAttribute User user,@RequestParam(value = "editRole") String[] editRole){
        user.setRoles(getRole(editRole));
        userService.saveUser(user);
        return "redirect:/admin_b";
    }

    @GetMapping("admin/user-delete/{id}")
    public String deleteUser(@PathVariable("id") int id){
        userService.deleteUser(id);
        return "redirect:/admin_b";
    }






}
