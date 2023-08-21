package com.conestoga.APIHousing.service;

import com.conestoga.APIHousing.dtos.LeaseDTO;
import com.conestoga.APIHousing.dtos.MaintenanceRequestDTO;
import com.conestoga.APIHousing.dtos.SubresidenceDTO;
import com.conestoga.APIHousing.interfaces.AccountRepository;
import com.conestoga.APIHousing.interfaces.LeaseRepository;
import com.conestoga.APIHousing.interfaces.SubresidenceRepository;
import com.conestoga.APIHousing.model.Account;
import com.conestoga.APIHousing.model.Lease;
import com.conestoga.APIHousing.model.Subresidence;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class LeaseService {
  private final LeaseRepository leaseRepository;
  private final SubresidenceRepository subresidenceRepository;
  private final AccountRepository accountRepository;
  private final UnitService unitService;

  private final EventService eventService;

  private final MaintenanceRequestService maintenanceRequestService;
  Logger logger = Logger.getLogger(LeaseService.class.getName());

  public int getTotalUsers() {
    return accountRepository.findAll().size();
  }

  public int getTotalUnits() {
    return unitService.getAllUnits().size();
  }

  public int getTotalEvents() {
    return eventService.getAllEvents().size();
  }

  public List<LeaseDTO> getLeasesByStatus(int status) {
    List<Lease> leases = leaseRepository.findByLeaseStatus(String.valueOf(status));
    return leases.stream()
            .map(this::convertToLeaseDTO)
            .collect(Collectors.toList());
  }

  public double getPercentageSolvedMaintenanceRequests() {
    List<MaintenanceRequestDTO> allMaintenanceRequests =
        maintenanceRequestService.getAllMaintenanceRequests();
    int totalMaintenanceRequests = allMaintenanceRequests.size();
    long solvedMaintenanceRequests =
        allMaintenanceRequests.stream().filter(mr -> mr.getRequestStatus() == 1).count();
    return (double) solvedMaintenanceRequests / totalMaintenanceRequests * 100;
  }

  public LeaseService(
      LeaseRepository leaseRepository,
      SubresidenceRepository subresidenceRepository,
      AccountRepository accountRepository,
      UnitService unitService, EventService eventService, MaintenanceRequestService maintenanceRequestService) {
    this.leaseRepository = leaseRepository;
    this.subresidenceRepository = subresidenceRepository;
    this.accountRepository = accountRepository;
    this.unitService = unitService;
    this.eventService = eventService;
    this.maintenanceRequestService = maintenanceRequestService;
  }

  public LeaseDTO createLease(LeaseDTO leaseDTO) {
    Lease lease = convertToLease(leaseDTO);
    lease.setLeaseStatus(0);
    Subresidence subresidence =
        subresidenceRepository.findById(leaseDTO.getSubresidenceId()).orElse(null);
    Account user = accountRepository.findById(leaseDTO.getUserId()).orElse(null);

    if (subresidence != null && user != null) {
      lease.setSubresience(subresidence);
      lease.setUser(user);
      Lease createdLease = leaseRepository.save(lease);
      logger.info("Lease created: " + createdLease.getLeaseId());
      return convertToLeaseDTO(createdLease);
    }
    logger.warning("Lease not created");
    return null;
  }

  public LeaseDTO getLeaseById(Long leaseId) {
    Optional<Lease> leaseOptional = leaseRepository.findById(leaseId);
    if (leaseOptional.isPresent()) {
      Lease lease = leaseOptional.get();
      logger.info("Lease found: " + lease.getLeaseId());
      return convertToLeaseDTO(lease);
    } else {
      logger.warning("Lease not found");
      return null;
    }
  }

  public LeaseDTO updateLease(Long leaseId, LeaseDTO leaseDTO) {
    Optional<Lease> leaseOptional = leaseRepository.findById(leaseId);
    if (leaseOptional.isPresent()) {
      Lease lease = leaseOptional.get();
      Subresidence Subresidence =
          subresidenceRepository.findById(leaseDTO.getSubresidenceId()).orElse(null);
      Account user = accountRepository.findById(leaseDTO.getUserId()).orElse(null);

      if (Subresidence != null && user != null) {
        lease.setSubresience(Subresidence);
        lease.setUser(user);
        lease.setLeaseStartDate(leaseDTO.getLeaseStartDate());
        lease.setLeaseEndDate(leaseDTO.getLeaseEndDate());
        lease.setLeaseStatus(leaseDTO.getLeaseStatus());
        lease.setUnitNo(leaseDTO.getUnitNo());

        Lease updatedLease = leaseRepository.save(lease);
        logger.info("Lease updated: " + updatedLease.getLeaseId());
        return convertToLeaseDTO(updatedLease);
      }
    }
    logger.warning("Lease not updated");
    return null;
  }

  public boolean deleteLease(Long leaseId) {
    if (leaseRepository.existsById(leaseId)) {
      leaseRepository.deleteById(leaseId);
      logger.info("Lease deleted: " + leaseId);
      return true;
    }
    logger.warning("Lease not deleted");
    return false;
  }

  public List<LeaseDTO> getAllLeases() {
    List<Lease> leases = leaseRepository.findAll();
    return leases.stream().map(this::convertToLeaseDTO).collect(Collectors.toList());
  }

  public List<LeaseDTO> getLeaseByUserId(Long userId) {
    Optional<Account> accountOptional = accountRepository.findById(userId);
    if (accountOptional.isEmpty()) {
      logger.warning("Account not found");
      return Collections.emptyList();
    }

    List<Lease> leases = leaseRepository.findByUser(accountOptional.get());
    return leases.stream().map(this::convertToLeaseDTO).collect(Collectors.toList());
  }

  private Lease convertToLease(LeaseDTO leaseDTO) {
    Lease lease = new Lease();
    lease.setLeaseStartDate(leaseDTO.getLeaseStartDate());
    lease.setLeaseEndDate(leaseDTO.getLeaseEndDate());
    lease.setLeaseStatus(leaseDTO.getLeaseStatus());
    lease.setUnitNo(leaseDTO.getUnitNo());
    return lease;
  }

  private LeaseDTO convertToLeaseDTO(Lease lease) {
    LeaseDTO leaseDTO = new LeaseDTO();
    leaseDTO.setLeaseId(lease.getLeaseId());
    leaseDTO.setUserId(lease.getUser().getId());
    leaseDTO.setSubresidenceId(lease.getSubresidence().getUnitId());
    leaseDTO.setSubresidence(new SubresidenceDTO(lease.getSubresidence()));
    leaseDTO.setUser(AccountService.convertToAccountDTO(lease.getUser()));
    leaseDTO.setLeaseStartDate(lease.getLeaseStartDate());
    leaseDTO.setLeaseEndDate(lease.getLeaseEndDate());
    leaseDTO.setLeaseStatus(lease.getLeaseStatus());
    leaseDTO.setUnitNo(lease.getUnitNo());
    return leaseDTO;
  }
}
