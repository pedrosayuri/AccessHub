package br.com.accesshub.access_hub.modules.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.accesshub.access_hub.exceptions.UserNotFoundException;
import br.com.accesshub.access_hub.modules.login.dto.LoginResponse;
import br.com.accesshub.access_hub.modules.login.service.LoginService;
import br.com.accesshub.access_hub.modules.users.entity.UserEntity;
import br.com.accesshub.access_hub.modules.users.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;
    
    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody UserEntity UserEntity) {
        try {
            var result = this.userService.create(UserEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/read/{uid}")
    public ResponseEntity<Object> read(@PathVariable String uid) {
        try {
            var result = this.userService.read(uid);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{uid}")
    public ResponseEntity<Object> update(@PathVariable String uid, @Valid @RequestBody UserEntity UserEntity) {
        try {
            var result = this.userService.update(uid, UserEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{uid}")
    public ResponseEntity<Object> delete(@PathVariable String uid) {
        try {
            this.userService.delete(uid);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody String uid) {
        try {
            LoginResponse loginResponse = loginService.login(uid);
            return ResponseEntity.ok().body(loginResponse);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
