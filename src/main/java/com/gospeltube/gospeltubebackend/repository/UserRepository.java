package com.gospeltube.gospeltubebackend.repository;

import com.gospeltube.gospeltubebackend.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUserId(Long userId);
    Optional<AppUser> findByEmail(String email);
    Optional<AppUser> findByChurchId(Long church_id);
    Optional<Set<AppUser>> findByChurchIdIsNotNull();
    Optional<Set<AppUser>> findByFirstNameContainingAndChurchIdIsNotNull(String firstName);

}
