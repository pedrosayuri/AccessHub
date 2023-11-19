package br.com.accesshub.access_hub.modules.users.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity(name = "users")
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "O campo [nome] é obrigatório!")
    private String nome;

    @NotBlank(message = "O campo [email] é obrigatório!")
    @Email(message = "O campo [email] deve conter um email válido!")
    private String email;

    @NotBlank(message = "O campo [senha] é obrigatório!")
    @Length(min = 6, max = 100, message = "O campo [senha] deve conter entre 6 e 100 caracteres!")
    private String senha;

    @NotBlank(message = "O campo [uid] é obrigatório!")
    private String uid;

    private double valor_hora;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime data_admissao;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
