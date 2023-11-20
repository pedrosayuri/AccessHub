package br.com.accesshub.access_hub.modules.login.service;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.accesshub.access_hub.modules.login.dto.LoginArgumentDTO;
import br.com.accesshub.access_hub.modules.login.dto.LoginResponse;
import br.com.accesshub.access_hub.modules.users.dto.AuthUserRequestDTO;
import br.com.accesshub.access_hub.modules.users.dto.UserDetails;
import br.com.accesshub.access_hub.modules.users.service.AuthUserService;
import br.com.accesshub.access_hub.modules.users.service.UserService;

@Service
public class LoginService {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthUserService authUserService;

    public LoginResponse login(LoginArgumentDTO loginArgumentDTO) throws AuthenticationException {
        UserDetails userDetails = userService.getUserDetailsByUid(loginArgumentDTO.getLogin());
        AuthUserRequestDTO authUserRequestDTO = new AuthUserRequestDTO(loginArgumentDTO.getLogin(), userDetails.getNome());
        String token = authUserService.execute(authUserRequestDTO).getAccess_token();
        return new LoginResponse(token);
    }
}
