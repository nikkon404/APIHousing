package com.conestoga.APIHousing.controller;

import com.conestoga.APIHousing.dtos.MaintenanceRequestDTO;
import com.conestoga.APIHousing.model.Notification;
import com.conestoga.APIHousing.service.MaintenanceRequestService;
import com.conestoga.APIHousing.service.NotificationService;
import com.conestoga.APIHousing.utils.Constants;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/maintenance-requests")
public class MaintenanceRequestController {

    private final MaintenanceRequestService maintenanceRequestService;
    private final NotificationService notificationService;

    public MaintenanceRequestController(MaintenanceRequestService maintenanceRequestService, NotificationService notificationService) {
        this.maintenanceRequestService = maintenanceRequestService;
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<MaintenanceRequestDTO> createMaintenanceRequest(
            @RequestBody MaintenanceRequestDTO maintenanceRequestDTO) throws IOException {
        MaintenanceRequestDTO createdMaintenanceRequest = maintenanceRequestService
                .createMaintenanceRequest(maintenanceRequestDTO);
        notificationService.create(new Notification("New maintainence request created!", maintenanceRequestDTO.getUserId(), Constants.NOTIFICATION_TYPE_MAINTENANCE));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMaintenanceRequest);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<MaintenanceRequestDTO> getMaintenanceRequestById(@PathVariable Long requestId) {
        MaintenanceRequestDTO maintenanceRequestDTO = maintenanceRequestService.getMaintenanceRequestById(requestId);
        if (maintenanceRequestDTO != null) {
            return ResponseEntity.ok(maintenanceRequestDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{requestId}")
    public ResponseEntity<MaintenanceRequestDTO> updateMaintenanceRequest(@PathVariable Long requestId,
                                                                          @RequestBody Map<String,String> reqBody ) {
          int status = Integer.parseInt( reqBody.get("requestStatus"));
          String remarks = reqBody.get("remarks");
        MaintenanceRequestDTO updatedMaintenanceRequest = maintenanceRequestService.updateMaintenanceRequest(requestId,
                status, remarks);
        if (updatedMaintenanceRequest != null) {
                    notificationService.create(new Notification("There is an update to your maintenance request", updatedMaintenanceRequest.getUserId(), Constants.NOTIFICATION_TYPE_MAINTENANCE));

            return ResponseEntity.ok(updatedMaintenanceRequest);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{requestId}")
    public ResponseEntity<Void> deleteMaintenanceRequest(@PathVariable Long requestId) {
        boolean deleted = maintenanceRequestService.deleteMaintenanceRequest(requestId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<MaintenanceRequestDTO>> getAllMaintenanceRequests() {
        List<MaintenanceRequestDTO> maintenanceRequests = maintenanceRequestService.getAllMaintenanceRequests();
        return ResponseEntity.ok(maintenanceRequests);
    }

    //get all maintenance requests by user id
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MaintenanceRequestDTO>> getAllMaintenanceRequestsByUserId(@PathVariable Long userId) {
        List<MaintenanceRequestDTO> maintenanceRequests = maintenanceRequestService.getAllMaintenanceRequestsByUserId(userId);
        return ResponseEntity.ok(maintenanceRequests);
    }
}
