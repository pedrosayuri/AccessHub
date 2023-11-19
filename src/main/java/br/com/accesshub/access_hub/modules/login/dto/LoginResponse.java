package br.com.accesshub.access_hub.modules.login.dto;

import br.com.accesshub.access_hub.modules.users.dto.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private UserDetails userDetails;
}
