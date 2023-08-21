package com.conestoga.APIHousing.interfaces;

import com.conestoga.APIHousing.model.Account;
import com.conestoga.APIHousing.model.Residence;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResidenceRepository extends JpaRepository<Residence, Long> {
    List<Residence> findByManager(Account manager);

    List<Residence> findByAddress(String address);

    List<Residence> findByName(String name);

}
