package br.com.accesshub.access_hub.modules.users.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.accesshub.access_hub.modules.users.dto.AuthUserRequestDTO;
import br.com.accesshub.access_hub.modules.users.dto.AuthUserResponseDTO;
import br.com.accesshub.access_hub.modules.users.repository.UserRepository;

@Service
public class AuthUserService {
    
    @Value("${security.token.secret.user}")
    private String secretKey;

    @Autowired
    private UserRepository userRepository;

    public AuthUserResponseDTO execute(AuthUserRequestDTO authUserRequestDTO) throws AuthenticationException {
        var user = this.userRepository.findByUid(authUserRequestDTO.uid())
            .orElseThrow(() -> {
                throw new UsernameNotFoundException("UID incorreto");
            });

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        var expiresIn = Instant.now().plus(Duration.ofHours(6));
        var token = JWT.create()
                .withIssuer("access-hub")
                .withSubject(user.getId().toString())
                .withClaim("uid", user.getUid())
                .withClaim("nome", user.getNome())
                .withClaim("roles", Arrays.asList("USER"))
                .withExpiresAt(expiresIn)
                .sign(algorithm);

        var authUserResponse = AuthUserResponseDTO.builder()
            .access_token(token)
            .expires_in(expiresIn.toEpochMilli())
            .build();

        return authUserResponse;
    }

}
