package com.conestoga.APIHousing.controller;

import com.conestoga.APIHousing.dtos.LeaseDTO;
import com.conestoga.APIHousing.model.Notification;
import com.conestoga.APIHousing.service.LeaseService;
import com.conestoga.APIHousing.service.NotificationService;
import com.conestoga.APIHousing.utils.Constants;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leases")
public class LeaseController {

    private final LeaseService leaseService;
    private final NotificationService notificationService;

    public LeaseController(LeaseService leaseService, NotificationService notificationService) {
        this.leaseService = leaseService;
        this.notificationService = notificationService;
    }

    @PostMapping("/create")
    public ResponseEntity<LeaseDTO> createLease(@RequestBody LeaseDTO leaseDTO) {
        try {
            LeaseDTO createdLease = leaseService.createLease(leaseDTO);

                         notificationService.create(new Notification("You have applied for lease", createdLease.getUserId(), Constants.NOTIFICATION_TYPE_Housing));

            
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLease);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{leaseId}")
    public ResponseEntity<LeaseDTO> getLeaseById(@PathVariable Long leaseId) {
        LeaseDTO leaseDTO = leaseService.getLeaseById(leaseId);
        if (leaseDTO != null) {
            return ResponseEntity.ok(leaseDTO);
        }
        return ResponseEntity.notFound().build();
    }
    //get list of leases by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LeaseDTO>> getLeaseByUserId(@PathVariable Long userId) {
        List<LeaseDTO> leaseDTO = leaseService.getLeaseByUserId(userId);
        if (leaseDTO != null) {
            return ResponseEntity.ok(leaseDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{leaseId}")
    public ResponseEntity<LeaseDTO> updateLease(@PathVariable Long leaseId, @RequestBody LeaseDTO leaseDTO) {
        try {
            LeaseDTO updatedLease = leaseService.updateLease(leaseId, leaseDTO);
            if (updatedLease != null) {
                String msg = "";
                if(updatedLease.getLeaseStatus()==1)
                {
                    msg = "Your lease for "+updatedLease.getUnitNo()+ " has been approved";
                }
                else{
                    msg = "Your lease for "+updatedLease.getUnitNo()+ " has been rejected";
                }
                         notificationService.create(new Notification(msg, updatedLease.getUserId(), Constants.NOTIFICATION_TYPE_Housing));

                return ResponseEntity.ok(updatedLease);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{leaseId}")
    public ResponseEntity<Void> deleteLease(@PathVariable Long leaseId) {
        boolean deleted = leaseService.deleteLease(leaseId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<LeaseDTO>> getAllLeases() {
        List<LeaseDTO> leases = leaseService.getAllLeases();
        return ResponseEntity.ok(leases);
    }

 
}
