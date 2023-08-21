package com.conestoga.APIHousing.service;

import com.conestoga.APIHousing.dtos.ResidenceDTO;
import com.conestoga.APIHousing.interfaces.AccountRepository;
import com.conestoga.APIHousing.interfaces.ResidenceRepository;
import com.conestoga.APIHousing.model.Account;
import com.conestoga.APIHousing.model.Residence;
import com.conestoga.APIHousing.model.Subresidence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ResidenceServiceTest {

    @Mock
    private ResidenceRepository residenceRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private ResidenceService residenceService;

    @BeforeEach
    void setUp() {
        // Reset the mock before each test
        reset(residenceRepository);
        reset(accountRepository);
    }

    @Test
    void testCreateResidence() {
        Long managerId = 1L;
        ResidenceDTO residenceDTO = new ResidenceDTO();
        residenceDTO.setName("Residence 1");
        residenceDTO.setImg(Optional.of("img"));

        Account manager = new Account();
        manager.setId(managerId);

        residenceDTO.setManagerId(manager.getId());
        Optional<Account> optionalManager = Optional.of(manager);

        when(accountRepository.findById(managerId)).thenReturn(optionalManager);
        when(residenceRepository.save(any(Residence.class))).thenReturn(residenceDTO.convertToResidence());

        ResidenceDTO result = residenceService.createResidence(residenceDTO);

        assertNotNull(result);
        assertEquals("Residence 1", result.getName());
        assertEquals(managerId, result.getManagerId());
        verify(residenceRepository, times(1)).save(any(Residence.class));
    }

    @Test
    void testCreateResidence_ManagerNotFound() {
        Long managerId = 1L;
        ResidenceDTO residenceDTO = new ResidenceDTO();
        residenceDTO.setName("Residence 1");
        residenceDTO.setManagerId(managerId);
        residenceDTO.setImg(Optional.of("img"));

        when(accountRepository.findById(managerId)).thenReturn(Optional.empty());

        when(accountRepository.findById(managerId)).thenReturn(Optional.empty());

        ResidenceDTO result = residenceService.createResidence(residenceDTO);

        assertNull(result);
        verify(residenceRepository, never()).save(any(Residence.class));
    }

    @Test
    void testGetResidenceById() {
        Long residenceId = 1L;
        Residence residence = new Residence();
        residence.setResidenceId(residenceId);
        residence.setName("Residence 1");
        residence.setManager(new Account());

        when(residenceRepository.findById(residenceId)).thenReturn(Optional.of(residence));

        ResidenceDTO result = residenceService.getResidenceById(residenceId);

        assertNotNull(result);
        assertEquals(residenceId, result.getResidenceId());
        assertEquals("Residence 1", result.getName());
        verify(residenceRepository, times(1)).findById(residenceId);
    }

    @Test
    void testGetResidenceById_NotFound() {
        Long residenceId = 1L;

        when(residenceRepository.findById(residenceId)).thenReturn(Optional.empty());

        ResidenceDTO result = residenceService.getResidenceById(residenceId);

        assertNull(result);
        verify(residenceRepository, times(1)).findById(residenceId);
    }

    @Test
    void testUpdateResidence() {
        Long residenceId = 1L;
        ResidenceDTO residenceDTO = new ResidenceDTO();
        residenceDTO.setName("Updated Residence");

        Residence existingResidence = new Residence();
        existingResidence.setResidenceId(residenceId);
        existingResidence.setName("Residence 1");
        existingResidence.setManager(new Account());

        when(residenceRepository.findById(residenceId)).thenReturn(Optional.of(existingResidence));
        when(residenceRepository.save(any(Residence.class))).thenReturn(existingResidence);

        ResidenceDTO result = residenceService.updateResidence(residenceId, residenceDTO);

        assertNotNull(result);
        assertEquals("Residence " + residenceId, result.getName());
        verify(residenceRepository, times(1)).save(existingResidence);
    }

    @Test
    void testUpdateResidence_NotFound() {
        Long residenceId = 1L;
        ResidenceDTO residenceDTO = new ResidenceDTO();
        residenceDTO.setName("Updated Residence");

        when(residenceRepository.findById(residenceId)).thenReturn(Optional.empty());

        ResidenceDTO result = residenceService.updateResidence(residenceId, residenceDTO);

        assertNull(result);
        verify(residenceRepository, never()).save(any(Residence.class));
    }

    @Test
    void testDeleteResidence() {
        Long residenceId = 1L;
        Residence residence = new Residence();
        residence.setResidenceId(residenceId);
        residence.setName("Residence 1");

        when(residenceRepository.findById(residenceId)).thenReturn(Optional.of(residence));

        boolean result = residenceService.deleteResidence(residenceId);

        assertTrue(result);
        verify(residenceRepository, times(1)).delete(residence);
    }

    @Test
    void testDeleteResidence_NotFound() {
        Long residenceId = 1L;

        when(residenceRepository.findById(residenceId)).thenReturn(Optional.empty());

        boolean result = residenceService.deleteResidence(residenceId);

        assertFalse(result);
        verify(residenceRepository, never()).delete(any(Residence.class));
    }

    @Test
    void testGetAllResidences() {
        List<Subresidence> subResidences = new ArrayList<>();
        subResidences.add(new Subresidence());

        Residence residence1 = new Residence();
        residence1.setResidenceId(1L);
        residence1.setName("Residence 1");
        residence1.setManager(new Account());
        residence1.setSubResidences(subResidences);

        Residence residence2 = new Residence();
        residence2.setResidenceId(2L);
        residence2.setName("Residence 2");
        residence2.setManager(new Account());

        subResidences.add(new Subresidence());
        residence2.setSubResidences(subResidences);


        List<Residence> residences = Arrays.asList(residence1, residence2);

        when(residenceRepository.findAll()).thenReturn(residences);

        List<ResidenceDTO> result = residenceService.getAllResidences();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Residence 1", result.get(0).getName());
        assertEquals("Residence 2", result.get(1).getName());
    }
}
