package com.conestoga.APIHousing.interfaces;

import com.conestoga.APIHousing.model.Account;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAll();
    Optional<Account> findByEmail(String email);

    List<Account> findByLastName(String lastName);

    List<Account> findByAddress(String address);

    List<Account> findByDateOfBirth(LocalDate dateOfBirth);

    //custum query, set password where email = e and id = i
    @Query("UPDATE Account a SET a.password = :password WHERE a.email = :email AND a.id = :id")
    int updatePassword(String password, String email, Long id);
}
