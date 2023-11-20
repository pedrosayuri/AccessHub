package br.com.accesshub.access_hub.modules.users.controller;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.accesshub.access_hub.exceptions.UserNotFoundException;
import br.com.accesshub.access_hub.modules.login.dto.LoginArgumentDTO;
import br.com.accesshub.access_hub.modules.login.dto.LoginResponse;
import br.com.accesshub.access_hub.modules.login.service.LoginService;
import br.com.accesshub.access_hub.modules.users.dto.UserDetails;
import br.com.accesshub.access_hub.modules.users.entity.UserEntity;
import br.com.accesshub.access_hub.modules.users.service.UserService;
import br.com.accesshub.access_hub.provider.JWTTokenProvider;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private JWTTokenProvider JwtTokenProvider;
    
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

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/update/{uid}")
    public ResponseEntity<Object> update(@PathVariable String uid, 
                                        @Valid @RequestBody UserEntity userEntity, 
                                        @RequestHeader("Authorization") String token) {
        try {
            UserDetails userDetails = JwtTokenProvider.getUserDetailsFromToken(token);
    
            if (!uid.equals(userDetails.getUid())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to update this user");
            }
    
            var result = this.userService.update(uid, userEntity);
            if (result == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update failed");
            }
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            if (errorMessage == null) {
                errorMessage = "An error occurred";
            }
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }
    

    @PreAuthorize("hasRole('USER')")
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
    public ResponseEntity<Object> login(@RequestBody LoginArgumentDTO loginArgumentDTO) throws AuthenticationException {
        try {
            LoginResponse loginResponse = loginService.login(loginArgumentDTO);
            return ResponseEntity.ok().body(loginResponse);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
