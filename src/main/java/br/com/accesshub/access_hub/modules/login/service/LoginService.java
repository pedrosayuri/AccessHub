package br.com.accesshub.access_hub.modules.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.accesshub.access_hub.modules.login.dto.LoginResponse;
import br.com.accesshub.access_hub.modules.users.dto.UserDetails;
import br.com.accesshub.access_hub.modules.users.service.UserService;

@Service
public class LoginService {

    @Autowired
    private UserService userService;

    public LoginResponse login(String uid) {
        UserDetails userDetails = userService.getUserDetailsByUid(uid);
        return new LoginResponse(userDetails);
    }
}
