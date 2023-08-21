package com.conestoga.APIHousing.interfaces;

import com.conestoga.APIHousing.model.Account;
import com.conestoga.APIHousing.model.MaintenanceRequest;
import com.conestoga.APIHousing.model.Subresidence;
import java.time.LocalDate;
import java.util.List;

import jnr.ffi.annotations.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceRequestRepository extends JpaRepository<MaintenanceRequest, Long> {
  List<MaintenanceRequest> findByUnit(Subresidence unit);

  List<MaintenanceRequest> findByUser(Account user);

  List<MaintenanceRequest> findByRequestStatus(Integer requestStatus);

  List<MaintenanceRequest> findByRequestDateAfter(LocalDate date);

  // find by user id and sort by request date
  List<MaintenanceRequest> findByUserIdOrderByRequestIdDesc(Long userId);
}
