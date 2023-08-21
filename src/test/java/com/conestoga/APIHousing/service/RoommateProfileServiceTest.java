package com.conestoga.APIHousing.service;

import com.conestoga.APIHousing.interfaces.AccountRepository;
import com.conestoga.APIHousing.interfaces.RoommateProfileRepository;
import com.conestoga.APIHousing.model.Account;
import com.conestoga.APIHousing.model.RoommateProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
class RoommateProfileServiceTest {

    @Mock
    private RoommateProfileRepository roommateProfileRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private RoommateProfileService roommateProfileService;

    @BeforeEach
    void setUp() {
        // Reset the mock before each test
        reset(roommateProfileRepository);
        reset(accountRepository);
    }

    @Test
    void testFindAll() {
        List<RoommateProfile> profiles = new ArrayList<>();
        // Add some profiles to the list

        when(roommateProfileRepository.findAll()).thenReturn(profiles);

        List<RoommateProfile> result = roommateProfileService.findAll();

        assertEquals(profiles, result);
        verify(roommateProfileRepository, times(1)).findAll();
    }

    @Test
    void testCreateRoommateProfile() {
        Long userId = 1L;
        RoommateProfile profile = new RoommateProfile();
        Account account = new Account();

        when(accountRepository.findById(userId)).thenReturn(Optional.of(account));
        when(roommateProfileRepository.save(any(RoommateProfile.class))).thenReturn(profile);

        RoommateProfile result = roommateProfileService.createRoommateProfile(userId, profile);

        assertEquals(profile, result);
        assertSame(account, profile.getUser());
        verify(roommateProfileRepository, times(1)).save(profile);
    }

    @Test
    void testCreateRoommateProfile_AccountNotFound() {
        Long userId = 1L;
        RoommateProfile profile = new RoommateProfile();

        when(accountRepository.findById(userId)).thenReturn(Optional.empty());

        RoommateProfile result = roommateProfileService.createRoommateProfile(userId, profile);

        assertNull(result);
        verify(roommateProfileRepository, never()).save(any(RoommateProfile.class));
    }

    @Test
    void testGetRoommateProfileById() {
        Long profileId = 1L;
        RoommateProfile profile = new RoommateProfile();

        when(roommateProfileRepository.findById(profileId)).thenReturn(Optional.of(profile));

        RoommateProfile result = roommateProfileService.getRoommateProfileById(profileId);

        assertEquals(profile, result);
        verify(roommateProfileRepository, times(1)).findById(profileId);
    }

    @Test
    void testGetRoommateProfileById_NotFound() {
        Long profileId = 1L;

        when(roommateProfileRepository.findById(profileId)).thenReturn(Optional.empty());

        RoommateProfile result = roommateProfileService.getRoommateProfileById(profileId);

        assertNull(result);
        verify(roommateProfileRepository, times(1)).findById(profileId);
    }

    @Test
    void testUpdateRoommateProfile() {
        Long profileId = 1L;
        RoommateProfile existingProfile = new RoommateProfile();
        RoommateProfile updatedProfile = new RoommateProfile();
        updatedProfile.setDescription("Updated Description");

        when(roommateProfileRepository.findById(profileId)).thenReturn(Optional.of(existingProfile));
        when(roommateProfileRepository.save(any(RoommateProfile.class))).thenReturn(updatedProfile);

        RoommateProfile result = roommateProfileService.updateRoommateProfile(profileId, updatedProfile);

        assertEquals(updatedProfile, result);
        assertEquals("Updated Description", existingProfile.getDescription());
        verify(roommateProfileRepository, times(1)).save(existingProfile);
    }

    @Test
    void testUpdateRoommateProfile_NotFound() {
        Long profileId = 1L;
        RoommateProfile updatedProfile = new RoommateProfile();

        when(roommateProfileRepository.findById(profileId)).thenReturn(Optional.empty());

        RoommateProfile result = roommateProfileService.updateRoommateProfile(profileId, updatedProfile);

        assertNull(result);
        verify(roommateProfileRepository, never()).save(any(RoommateProfile.class));
    }

    @Test
    void testDeleteRoommateProfile() {
        Long profileId = 1L;

        roommateProfileService.deleteRoommateProfile(profileId);

        verify(roommateProfileRepository, times(1)).deleteById(profileId);
    }

    @Test
    void testGetRoommateProfileByUserId() {
        Long userId = 1L;
        RoommateProfile profile = new RoommateProfile();

        when(roommateProfileRepository.findByUserId(userId)).thenReturn(Optional.of(profile));

        Optional<RoommateProfile> result = roommateProfileService.getRoommateProfileByUserId(userId);

        assertTrue(result.isPresent());
        assertEquals(profile, result.get());
        verify(roommateProfileRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testGetRoommateProfileByUserId_NotFound() {
        Long userId = 1L;

        when(roommateProfileRepository.findByUserId(userId)).thenReturn(Optional.empty());

        Optional<RoommateProfile> result = roommateProfileService.getRoommateProfileByUserId(userId);

        assertFalse(result.isPresent());
        verify(roommateProfileRepository, times(1)).findByUserId(userId);
    }
}
