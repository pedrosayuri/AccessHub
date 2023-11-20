package br.com.accesshub.access_hub.modules.users.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetails {
    private UUID userId;
    private String nome;
    private String email;
    private String uid;
}