package br.com.accesshub.access_hub.modules.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.accesshub.access_hub.exceptions.UserFoundException;
import br.com.accesshub.access_hub.exceptions.UserNotFoundException;
import br.com.accesshub.access_hub.modules.users.dto.UserDetails;
import br.com.accesshub.access_hub.modules.users.entity.UserEntity;
import br.com.accesshub.access_hub.modules.users.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity create(UserEntity userEntity) {
        this.userRepository
            .findByUidOrNomeOrEmail(userEntity.getUid(), userEntity.getNome(), userEntity.getEmail())
            .ifPresent(user -> {
                throw new UserFoundException();
            });

        var password = passwordEncoder.encode(userEntity.getSenha());
        userEntity.setSenha(password);

        return this.userRepository.save(userEntity);
    }

    public UserEntity read(String uid) {
        return userRepository.findByUid(uid)
                .orElseThrow(() -> new UserNotFoundException(uid));
    }

    public UserEntity update(String uid, UserEntity userEntity) {
        var user = this.userRepository.findByUid(uid)
                .orElseThrow(() -> new UserNotFoundException(uid));

        user.setNome(userEntity.getNome());
        user.setEmail(userEntity.getEmail());
        user.setSenha(userEntity.getSenha());
        user.setValor_hora(userEntity.getValor_hora());
        user.setUid(userEntity.getUid());

        return this.userRepository.save(user);
    }

    public void delete(String uid) {
        var user = this.userRepository.findByUid(uid)
                .orElseThrow(() -> new UserNotFoundException(uid));

        this.userRepository.delete(user);
    }

    public UserDetails getUserDetailsByUid(String uid) {
        UserEntity user = userRepository.findByUid(uid)
                .orElseThrow(() -> new UserNotFoundException(uid));

        UserDetails userDetails = new UserDetails(user.getId(), user.getNome(), user.getEmail());
        return userDetails;
    }
}
