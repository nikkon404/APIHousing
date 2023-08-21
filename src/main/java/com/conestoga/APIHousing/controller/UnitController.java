package com.conestoga.APIHousing.controller;

import com.conestoga.APIHousing.model.Subresidence;
import com.conestoga.APIHousing.service.UnitService;
import java.io.IOException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/units")
public class UnitController {

    private final UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @PostMapping
    public ResponseEntity<Subresidence> createUnit(@RequestBody Subresidence unit) throws IOException {
        Subresidence createdUnit = unitService.createUnit(unit);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUnit);
    }

    @GetMapping("/{unitId}")
    public ResponseEntity<Subresidence> getUnitById(@PathVariable Long unitId) {
        Subresidence unit = unitService.getUnitById(unitId);
        if (unit != null) {
            return ResponseEntity.ok(unit);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{unitId}")
    public ResponseEntity<Subresidence> updateUnit(@PathVariable Long unitId, @RequestBody Subresidence unit) throws IOException {
        Subresidence updatedUnit = unitService.updateUnit(unitId, unit);
        if (updatedUnit != null) {
            return ResponseEntity.ok(updatedUnit);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{unitId}")
    public ResponseEntity<Void> deleteUnit(@PathVariable Long unitId) {
        boolean deleted = unitService.deleteUnit(unitId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Subresidence>> getAllUnits() {
        List<Subresidence> units = unitService.getAllUnits();
        return ResponseEntity.ok(units);
    }

}
