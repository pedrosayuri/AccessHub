package br.com.accesshub.access_hub.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String uid) {
        super("Usuário não encontrado com o UID: " + uid);
    }
}