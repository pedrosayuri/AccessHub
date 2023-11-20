package br.com.accesshub.access_hub.provider;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.accesshub.access_hub.modules.users.dto.UserDetails;

@Service
public class JWTTokenProvider {

    @Value("${security.token.secret.user}")
    private String secretKey;

    public String getUserFromToken(String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
    
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(secretKey))
                    .build()
                    .verify(token);
    
            return jwt.getClaim("uid").asString() + " " + jwt.getClaim("nome").asString() + " " + jwt.getClaim("email").asString();
    
        } catch (Exception e) {
            throw new SecurityException("Token inválido");
        }
    }

    public UserDetails getUserDetailsFromToken(String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
    
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(secretKey))
                    .build()
                    .verify(token);
    
            String id = jwt.getSubject();
            String uid = jwt.getClaim("uid").asString();
            String nome = jwt.getClaim("nome").asString();
            String email = jwt.getClaim("email").asString();
    
            return new UserDetails(UUID.fromString(id), uid, nome, email);
        } catch (Exception e) {
            throw new SecurityException("Token inválido");
        }
    }
    
}