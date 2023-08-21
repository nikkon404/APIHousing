package com.conestoga.APIHousing.service;

import com.conestoga.APIHousing.dtos.ResidenceDTO;
import com.conestoga.APIHousing.interfaces.AccountRepository;
import com.conestoga.APIHousing.interfaces.ResidenceRepository;
import com.conestoga.APIHousing.model.Account;
import com.conestoga.APIHousing.model.Residence;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ResidenceService {
    private final ResidenceRepository residenceRepository;
    private final AccountRepository accountRepository;
    Logger logger = Logger.getLogger(ResidenceService.class.getName());

    public ResidenceService(ResidenceRepository residenceRepository, AccountRepository accountRepository) {
        this.residenceRepository = residenceRepository;
        this.accountRepository = accountRepository;
    }

    public ResidenceDTO createResidence(ResidenceDTO residenceDTO) {
        Residence residence = residenceDTO.convertToResidence();
        Account manager = accountRepository.findById(residenceDTO.getManagerId()).orElse(null);
        if (manager != null) {
            residence.setManager(manager);
            Residence createdResidence = residenceRepository.save(residence);
            logger.info("Residence created: " + createdResidence.getName());
            return new ResidenceDTO(createdResidence);
        }
        logger.warning("Manager not found for id: " + residenceDTO.getManagerId());
        return null;
    }

    public ResidenceDTO getResidenceById(Long residenceId) {
        Residence residence = residenceRepository.findById(residenceId).orElse(null);
        if (residence != null) {
            logger.info("Residence found: " + residence.getName());
            return new ResidenceDTO(residence);
        }
        logger.warning("Residence not found for id: " + residenceId);
        return null;
    }

    public ResidenceDTO updateResidence(Long residenceId, ResidenceDTO residenceDTO) {
        Residence existingResidence = residenceRepository.findById(residenceId).orElse(null);
        if (existingResidence != null) {
            existingResidence.setManager(existingResidence.getManager());
            Residence savedResidence = residenceRepository.save(existingResidence);
            logger.info("Residence updated: " + savedResidence.getName());
            return new ResidenceDTO(savedResidence);
        }
        logger.warning("Residence not found for id: " + residenceId);
        return null;
    }

    public boolean deleteResidence(Long residenceId) {
        Residence residence = residenceRepository.findById(residenceId).orElse(null);
        if (residence != null) {
            residenceRepository.delete(residence);
            logger.info("Residence deleted: " + residence.getName());
            return true;
        }
        logger.warning("Residence not found for id: " + residenceId);
        return false;
    }

    public List<ResidenceDTO> getAllResidences() {
        List<Residence> residences = residenceRepository.findAll();

        //stream and map and convert to ResidenceDTO by ResidenceDTO constructor
        return residences.stream().map(ResidenceDTO::new).collect(Collectors.toList());
    }


}

