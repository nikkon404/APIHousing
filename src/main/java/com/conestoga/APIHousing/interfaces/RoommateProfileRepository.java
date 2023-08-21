package com.conestoga.APIHousing.interfaces;

import com.conestoga.APIHousing.model.RoommateProfile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoommateProfileRepository extends JpaRepository<RoommateProfile, Long> {
  // Add any custom queries or methods if needed
  Optional<RoommateProfile> findByUserId(Long userId);
}
