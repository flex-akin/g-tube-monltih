package com.gospeltube.gospeltubebackend.repository;

import com.gospeltube.gospeltubebackend.entity.Church;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChurchRepository extends JpaRepository<Church, Long> {
   Optional<Church> findBySubaccount(String subaccount);
}
