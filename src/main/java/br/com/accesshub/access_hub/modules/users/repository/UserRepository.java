package br.com.accesshub.access_hub.modules.users.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.accesshub.access_hub.modules.users.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByUidOrNomeOrEmail(String uid, String nome, String email);
    Optional<UserEntity> findByUid(String uid);
}
