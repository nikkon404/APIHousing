package com.conestoga.APIHousing.interfaces;

import com.conestoga.APIHousing.model.Account;
import com.conestoga.APIHousing.model.Lease;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaseRepository extends JpaRepository<Lease, Long> {
  List<Lease> findByUser(Account user);

  List<Lease> findByLeaseStatus(String leaseStatus);

  List<Lease> findByLeaseStartDateAfter(LocalDate date);

  List<Lease> findByLeaseEndDateAfter(LocalDate date);
}
