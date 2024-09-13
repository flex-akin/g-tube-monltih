package com.gospeltube.gospeltubebackend.repository;

import com.gospeltube.gospeltubebackend.dto.ChurchEnableOFreeze;
import com.gospeltube.gospeltubebackend.dto.ChurchFAdmin;
import com.gospeltube.gospeltubebackend.entity.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    @Query(value = """
select a.user_id as id,  a.first_name as churchName, b.description, b.logo, a.email, b.address, b.city, b.province,
       b.country, b.zip as zipCode, b.doc from church b, users a where a.user_id = :id and a.church_id = b.id
""",
    nativeQuery = true)
    ChurchFAdmin getChurchForAdmin(@Param("id") Long id);

    @Query(
            value = """
            SELECT a.user_id AS id, a.first_name AS churchName, b.logo, a.is_enabled AS enabled
            FROM users a, church b
            WHERE a.church_id = b.id
            """,
            countQuery = """
            SELECT COUNT(a.user_id)
            FROM users a, church b
            WHERE a.church_id = b.id
            """,
            nativeQuery = true
    )
    Page<ChurchEnableOFreeze> getChurchFEnable2(Pageable pageable);
    
    @Query(
        value = """
        select a.user_id as id, a.first_name as churchName, b.logo, a.is_enabled as enabled
        from users a, church b where a.church_id = b.id
        """, nativeQuery = true
    )
    Set<ChurchEnableOFreeze> getChurchFEnable();
}
