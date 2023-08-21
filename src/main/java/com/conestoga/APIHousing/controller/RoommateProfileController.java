package com.conestoga.APIHousing.controller;


import com.conestoga.APIHousing.model.RoommateProfile;
import com.conestoga.APIHousing.service.AccountService;
import com.conestoga.APIHousing.service.RoommateProfileService;
import com.conestoga.APIHousing.utils.ErrorResponse;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roommate_profiles")
public class RoommateProfileController {
    
    private final RoommateProfileService roommateProfileService;

    
    @Autowired
    public RoommateProfileController(RoommateProfileService roommateProfileService, AccountService accountService) {
        this.roommateProfileService = roommateProfileService;
    }

     @PostMapping("/{user_id}")
    public ResponseEntity<RoommateProfile> createRoommateProfile(@PathVariable Long user_id, @RequestBody RoommateProfile roommateProfile) {
      
        RoommateProfile createdProfile = roommateProfileService.createRoommateProfile(user_id, roommateProfile);
        return new ResponseEntity<>(createdProfile, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RoommateProfile>> getAllAccounts() {
        List<RoommateProfile> accounts = roommateProfileService.findAll();
        return ResponseEntity.ok(accounts);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getRoommateProfileById(@PathVariable Long id) {
       Optional<RoommateProfile> profile = roommateProfileService.getRoommateProfileByUserId(id);
          if (profile.isPresent()) {
                    return new ResponseEntity<>(profile.get(), HttpStatus.OK);

        }

        return new ResponseEntity<>(new ErrorResponse(
            HttpStatus.NOT_FOUND, "No profile found"
            

        ) , HttpStatus.NOT_FOUND);
        
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoommateProfile> updateRoommateProfile(@PathVariable Long id, @RequestBody RoommateProfile roommateProfile) {
        RoommateProfile updatedProfile = roommateProfileService.updateRoommateProfile(id, roommateProfile);
        return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoommateProfile(@PathVariable Long id) {
        roommateProfileService.deleteRoommateProfile(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

      @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String errorMessage = "fuk u bitch";
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT, errorMessage);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

}
