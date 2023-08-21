package com.conestoga.APIHousing.interfaces;

import com.conestoga.APIHousing.model.Pin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PinRepository extends JpaRepository<Pin, Long> {

  @Query(
      "SELECT p FROM Pin p WHERE p.email = :email AND p.pinCode = :pinCode ORDER BY p.createdAt DESC")
  Pin findPinByEmail(String email, String pinCode);
}
