package com.conestoga.APIHousing.service;

import com.conestoga.APIHousing.interfaces.AccountRepository;
import com.conestoga.APIHousing.interfaces.RoommateProfileRepository;
import com.conestoga.APIHousing.model.Account;
import com.conestoga.APIHousing.model.RoommateProfile;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RoommateProfileService {
    private final RoommateProfileRepository roommateProfileRepository;
    private final AccountRepository accountRepository;
    Logger logger = Logger.getLogger(RoommateProfileService.class.getName());


    @Autowired
    public RoommateProfileService(RoommateProfileRepository roommateProfileRepository, AccountRepository accountRepository) {
        this.roommateProfileRepository = roommateProfileRepository;
        this.accountRepository = accountRepository;
    }

    public List<RoommateProfile> findAll() {
        logger.info("RoommateProfileService: findAll");
        return roommateProfileRepository.findAll();
    }


    public RoommateProfile createRoommateProfile(Long userId, RoommateProfile roommateProfile) {
        Optional<Account> optionalAccount = accountRepository.findById(userId);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            roommateProfile.setUser(account);
            logger.info("RoommateProfileService: createRoommateProfile");
            return roommateProfileRepository.save(roommateProfile);
        } else {
            logger.warning("RoommateProfileService: createRoommateProfile: Account not created");
            return null;
        }

    }

    public RoommateProfile getRoommateProfileById(Long id) {
        logger.info("RoommateProfileService: getRoommateProfileById for id: " + id);
        return roommateProfileRepository.findById(id).orElse(null);
    }


    public RoommateProfile updateRoommateProfile(Long id, RoommateProfile roommateProfile) {
        RoommateProfile existingProfile = getRoommateProfileById(id);
        if (existingProfile != null) {
            // Update the fields of existingProfile with the values from roommateProfile
            existingProfile.setDescription(roommateProfile.getDescription());
            existingProfile.setSmoking(roommateProfile.getSmoking());
            existingProfile.setDrinking(roommateProfile.getDrinking());
            existingProfile.setPets(roommateProfile.getPets());
            existingProfile.setHobbies(roommateProfile.getHobbies());
            existingProfile.setContactMethod(roommateProfile.getContactMethod());

            return roommateProfileRepository.save(existingProfile);
        }
        logger.warning("RoommateProfileService: updateRoommateProfile: RoommateProfile not updated");
        return null;
    }


    public void deleteRoommateProfile(Long id) {
        logger.info("RoommateProfileService: deleteRoommateProfile for id: " + id);
        roommateProfileRepository.deleteById(id);
    }

    public Optional<RoommateProfile> getRoommateProfileByUserId(Long userId) {
        logger.info("RoommateProfileService: getRoommateProfileByUserId for userId: " + userId);
        return roommateProfileRepository.findByUserId(userId);
    }
}
