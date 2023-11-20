package br.com.accesshub.access_hub.provider;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.accesshub.access_hub.modules.users.dto.UserDetails;

@Service
public class JWTProvider {

    @Value("${security.token.secret.user}")
    private String secretKey;

    public DecodedJWT validateToken(String token) {
        token = token.replace("Bearer ", "");

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        try {
            var tokenDecoded =  JWT.require(algorithm)
                .build()
                .verify(token);
            return tokenDecoded;
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    public UserDetails extractUserDetailsFromDecodedJWT(DecodedJWT decodedJWT) {
        String id = decodedJWT.getSubject();
        String uid = decodedJWT.getClaim("uid").asString();
        String nome = decodedJWT.getClaim("nome").asString();
        String email = decodedJWT.getClaim("email").asString();

        return new UserDetails(UUID.fromString(id), nome, uid, email);
    }

}
