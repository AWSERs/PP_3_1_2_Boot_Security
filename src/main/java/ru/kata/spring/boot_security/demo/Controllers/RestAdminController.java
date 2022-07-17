package ru.kata.spring.boot_security.demo.Controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/restadmin")
public class RestAdminController {

    private final UserService userService;


    @Autowired
    public RestAdminController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
    }

    @GetMapping("/adminpage")
    public ResponseEntity<List<User>> userList() {
        final List<User> users = userService.findAll();
        return users != null && !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("/adminpage/new")
    public List<User> addUser(@RequestBody User user) {
        userService.saveUser(user);
        return userService.findAll();
    }

    @PutMapping("/adminpage/edit")
    public ResponseEntity<?> update(@RequestBody User user) {
        userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/adminpage/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable Long id) {
        userService.deleteById(userService.getUserById(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
