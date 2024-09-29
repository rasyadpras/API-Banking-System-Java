package com.project.banking.repository;

import com.project.banking.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, String> {
    @Query(
            value = "SELECT * FROM profiles p WHERE LOWER(p.city) LIKE LOWER(CONCAT('%', :city, '%'))",
            nativeQuery = true
    )
    List<Profile> findAllByCity(@Param("city") String city);
}
