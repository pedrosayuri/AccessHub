package br.com.accesshub.access_hub.modules.users.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUserResponseDTO {
    
    private UUID id;
    private String uid;
    private String nome;
    private String email;

}
