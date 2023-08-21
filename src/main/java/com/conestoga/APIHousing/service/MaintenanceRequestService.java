package com.conestoga.APIHousing.service;

import com.conestoga.APIHousing.dtos.MaintenanceRequestDTO;
import com.conestoga.APIHousing.interfaces.AccountRepository;
import com.conestoga.APIHousing.interfaces.MaintenanceRequestRepository;
import com.conestoga.APIHousing.interfaces.SubresidenceRepository;
import com.conestoga.APIHousing.model.Account;
import com.conestoga.APIHousing.model.MaintenanceRequest;
import com.conestoga.APIHousing.model.Subresidence;
import com.conestoga.APIHousing.utils.FileUpload;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

@Service
public class MaintenanceRequestService {
    private final MaintenanceRequestRepository maintenanceRequestRepository;
    private final SubresidenceRepository unitRepository;
    private final AccountRepository accountRepository;
    Logger logger = Logger.getLogger(MaintenanceRequestService.class.getName());

    public MaintenanceRequestService(MaintenanceRequestRepository maintenanceRequestRepository,
                                     SubresidenceRepository unitRepository,
                                     AccountRepository accountRepository) {
        this.maintenanceRequestRepository = maintenanceRequestRepository;
        this.unitRepository = unitRepository;
        this.accountRepository = accountRepository;
    }

    public MaintenanceRequestDTO createMaintenanceRequest(MaintenanceRequestDTO maintenanceRequestDTO) throws IOException {
        MaintenanceRequest maintenanceRequest = maintenanceRequestDTO.toModel();
        Subresidence unit = unitRepository.findById(maintenanceRequestDTO.getUnitId()).orElse(null);
        Account user = accountRepository.findById(maintenanceRequestDTO.getUserId()).orElse(null);
        if (unit != null && user != null) {
            maintenanceRequest.setUnit(unit);
            maintenanceRequest.setUser(user);

            //if dto's date is null, set current date
            if (maintenanceRequestDTO.getRequestDate() == null) {
                logger.info("Date is null");
                maintenanceRequest.setRequestDate(new java.util.Date());
                logger.info("Date is set to current date");
            }


            //if image is not null, upload image
            if (maintenanceRequestDTO.getImg() != null) {
                logger.info("Image is not null");
                maintenanceRequest.setImg(FileUpload.convertBase64ToFile((maintenanceRequestDTO.getImg())));
                logger.info("Image is uploaded");
            }
            MaintenanceRequest createdMaintenanceRequest = maintenanceRequestRepository.save(maintenanceRequest);
            return MaintenanceRequestDTO.fromModel(createdMaintenanceRequest);
        }
        logger.warning("Unit or user is null");
        return null;
    }


    public MaintenanceRequestDTO getMaintenanceRequestById(Long requestId) {
        MaintenanceRequest maintenanceRequest = maintenanceRequestRepository.findById(requestId).orElse(null);
        if (maintenanceRequest != null) {
            logger.info("Maintenance request found for id: " + requestId);
            return MaintenanceRequestDTO.fromModel(maintenanceRequest);
        }
        logger.warning("Maintenance request not found for id: " + requestId);
        return null;
    }

    public MaintenanceRequestDTO updateMaintenanceRequest(Long requestId, int status, String remarks) {
        MaintenanceRequest existingMaintenanceRequest = maintenanceRequestRepository.findById(requestId).orElse(null);
        if (existingMaintenanceRequest != null) {
            logger.info("Maintenance request found for id: " + requestId);
            existingMaintenanceRequest.setRequestId(requestId);
            existingMaintenanceRequest.setRequestDescription(existingMaintenanceRequest.getRequestDescription());
            existingMaintenanceRequest.setRequestStatus(status);
            existingMaintenanceRequest.setRemarks(remarks);
            MaintenanceRequest savedMaintenanceRequest = maintenanceRequestRepository.save(existingMaintenanceRequest);
            logger.info("Maintenance request updated for id: " + requestId);
            return MaintenanceRequestDTO.fromModel(savedMaintenanceRequest);
        }
        logger.warning("Maintenance request not found for id: " + requestId);
        return null;
    }

    public boolean deleteMaintenanceRequest(Long requestId) {
        MaintenanceRequest maintenanceRequest = maintenanceRequestRepository.findById(requestId).orElse(null);
        if (maintenanceRequest != null) {
            maintenanceRequestRepository.delete(maintenanceRequest);
            logger.info("Maintenance request deleted for id: " + requestId);
            return true;
        }
        logger.warning("Maintenance request not found for id: " + requestId);
        return false;
    }

    public List<MaintenanceRequestDTO> getAllMaintenanceRequests() {
        List<MaintenanceRequest> maintenanceRequests = maintenanceRequestRepository.findAll();
    List<MaintenanceRequestDTO> maintenanceRequestDTOS = new ArrayList<>();
        for (MaintenanceRequest maintenanceRequest : maintenanceRequests) {
            maintenanceRequestDTOS.add(MaintenanceRequestDTO.fromModel(maintenanceRequest));
        }
        logger.info("All maintenance requests found");
        return maintenanceRequestDTOS;


    }
    public List<MaintenanceRequestDTO> getAllMaintenanceRequestsByUserId(Long userId) {

        List<MaintenanceRequest> maintenanceRequests = maintenanceRequestRepository.findByUserIdOrderByRequestIdDesc(userId);
        List<MaintenanceRequestDTO> maintenanceRequestDTOS = new ArrayList<>();
        for (MaintenanceRequest maintenanceRequest : maintenanceRequests) {
            maintenanceRequestDTOS.add(MaintenanceRequestDTO.fromModel(maintenanceRequest));
        }
        logger.info("All maintenance requests found for user id: " + userId);
        return maintenanceRequestDTOS;

    }

    public int getSolvedMaintenanceRequestsCount() {
        List<MaintenanceRequest> solvedRequests = maintenanceRequestRepository.findByRequestStatus(Integer.parseInt("1"));
        logger.info("Solved maintenance requests count: " + solvedRequests.size());
        return solvedRequests.size();
    }
}

