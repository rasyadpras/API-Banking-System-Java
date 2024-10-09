package com.project.banking.repository;

import com.project.banking.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, String> {
    @Query(
            value = "SELECT * FROM profiles p WHERE LOWER(p.city) LIKE LOWER(CONCAT('%', :city, '%'))",
            nativeQuery = true
    )
    List<Profile> findAllByCity(@Param("city") String city);
}
