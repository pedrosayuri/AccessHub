package br.com.accesshub.access_hub.modules.users.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.accesshub.access_hub.modules.users.dto.ProfileUserResponseDTO;
import br.com.accesshub.access_hub.modules.users.repository.UserRepository;

@Service
public class ProfileUserService {
    
    @Autowired
    private UserRepository userRepository;

    public ProfileUserResponseDTO execute(UUID userId) {
        var user = this.userRepository.findById(userId)
            .orElseThrow(() -> {
                throw new UsernameNotFoundException("User not found with uid");
            });

        var userDTO = ProfileUserResponseDTO.builder()
            .id(user.getId())
            .uid(user.getUid())
            .nome(user.getNome())
            .email(user.getEmail())
            .build();

        return userDTO;
    }

}
