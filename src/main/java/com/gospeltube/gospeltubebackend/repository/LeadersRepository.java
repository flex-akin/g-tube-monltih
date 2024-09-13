package com.gospeltube.gospeltubebackend.repository;

import com.gospeltube.gospeltubebackend.entity.Leaders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface LeadersRepository extends JpaRepository<Leaders, Long> {
   @Query(value ="""
select * from church_leaders where church_id = :aLong
""", nativeQuery = true)
    Optional<Set<Leaders>> findByChurchId(Long aLong);
}
