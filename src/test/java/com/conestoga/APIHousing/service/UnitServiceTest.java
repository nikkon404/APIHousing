package com.conestoga.APIHousing.service;

import com.conestoga.APIHousing.interfaces.ResidenceRepository;
import com.conestoga.APIHousing.interfaces.SubresidenceRepository;
import com.conestoga.APIHousing.model.Subresidence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnitServiceTest {

  @Mock private SubresidenceRepository unitRepository;

  @Mock private ResidenceRepository residenceRepository;

  @InjectMocks private UnitService unitService;

  @Test
  void testCreateUnit() throws IOException {
    String base64ImageData =
        "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNk+A8AAQUBAScY42YAAAAASUVORK5CYII=";
    Subresidence unit = new Subresidence();
    unit.setImg(base64ImageData);

    when(unitRepository.save(any(Subresidence.class)))
        .thenAnswer(
            invocation -> {
              Subresidence savedUnit = invocation.getArgument(0);
              savedUnit.setImg(base64ImageData); // Set the image data after it's saved
              return savedUnit;
            });

    Subresidence createdUnit = unitService.createUnit(unit);

    assertEquals(base64ImageData, createdUnit.getImg());
    verify(unitRepository, times(1)).save(unit);
  }

  @Test
  void testGetUnitById() {
    Long unitId = 1L;
    Subresidence unit = new Subresidence();
    unit.setUnitId(unitId);

    when(unitRepository.findById(unitId)).thenReturn(Optional.of(unit));

    Subresidence foundUnit = unitService.getUnitById(unitId);

    assertEquals(unitId, foundUnit.getUnitId());
    verify(unitRepository, times(1)).findById(unitId);
  }

  @Test
  void testGetUnitByIdNotFound() {
    Long unitId = 1L;

    when(unitRepository.findById(unitId)).thenReturn(Optional.empty());

    Subresidence foundUnit = unitService.getUnitById(unitId);

    assertNull(foundUnit);
    verify(unitRepository, times(1)).findById(unitId);
  }

  @Test
  void testUpdateUnit() throws IOException {
    Long unitId = 1L;
    String base64ImageData = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNk+A8AAQUBAScY42YAAAAASUVORK5CYII=";

    // Existing unit with different image path
    Subresidence existingUnit = new Subresidence();
    existingUnit.setUnitId(unitId);
    existingUnit.setImg("existing_image_path");

    // Updated unit with the expected base64 image data
    Subresidence updatedUnit = new Subresidence();
    updatedUnit.setUnitId(unitId);
    updatedUnit.setImg(base64ImageData);

    when(unitRepository.findById(unitId)).thenReturn(Optional.of(existingUnit));
    when(unitRepository.save(any(Subresidence.class))).thenReturn(updatedUnit);

    Subresidence resultUnit = unitService.updateUnit(unitId, updatedUnit);

    assertEquals(base64ImageData, resultUnit.getImg());
    verify(unitRepository, times(1)).findById(unitId);
    verify(unitRepository, times(1)).save(any(Subresidence.class));
  }



  @Test
  void testUpdateUnitNotFound() throws IOException {
    Long unitId = 1L;

    when(unitRepository.findById(unitId)).thenReturn(Optional.empty());

    assertThrows(IOException.class, () -> unitService.updateUnit(unitId, new Subresidence()));
    verify(unitRepository, times(1)).findById(unitId);
    verify(unitRepository, never()).save(any(Subresidence.class));
  }

  @Test
  void testDeleteUnit() {
    Long unitId = 1L;
    Subresidence unit = new Subresidence();
    unit.setUnitId(unitId);

    when(unitRepository.findById(unitId)).thenReturn(Optional.of(unit));

    boolean result = unitService.deleteUnit(unitId);

    assertTrue(result);
    verify(unitRepository, times(1)).findById(unitId);
    verify(unitRepository, times(1)).delete(unit);
  }

  @Test
  void testDeleteUnitNotFound() {
    Long unitId = 1L;

    when(unitRepository.findById(unitId)).thenReturn(Optional.empty());

    boolean result = unitService.deleteUnit(unitId);

    assertFalse(result);
    verify(unitRepository, times(1)).findById(unitId);
    verify(unitRepository, never()).delete(any(Subresidence.class));
  }

  @Test
  void testGetAllUnits() {
    List<Subresidence> units = new ArrayList<>();
    units.add(new Subresidence());
    units.add(new Subresidence());

    when(unitRepository.findAll()).thenReturn(units);

    List<Subresidence> foundUnits = unitService.getAllUnits();

    assertEquals(2, foundUnits.size());
    verify(unitRepository, times(1)).findAll();
  }
}
