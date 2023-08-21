package com.conestoga.APIHousing.service;

import com.conestoga.APIHousing.interfaces.ResidenceRepository;
import com.conestoga.APIHousing.interfaces.SubresidenceRepository;
import com.conestoga.APIHousing.model.Subresidence;
import com.conestoga.APIHousing.utils.FileUpload;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

@Service
public class UnitService {
    private final SubresidenceRepository unitRepository;
    private final ResidenceRepository residenceRepository;
    Logger logger = Logger.getLogger(UnitService.class.getName());

    public UnitService(SubresidenceRepository unitRepository, ResidenceRepository residenceRepository) {
        this.unitRepository = unitRepository;
        this.residenceRepository = residenceRepository;
    }

    public Subresidence createUnit(Subresidence unit) throws IOException {
        unit.setImg(FileUpload.convertBase64ToFile(unit.getImg()));
        logger.info("Unit created: " + unit);
        return unitRepository.save(unit);

    }

    public Subresidence getUnitById(Long unitId) {
        Optional<Subresidence> unitOptional = unitRepository.findById(unitId);
        if (unitOptional.isPresent()){
            logger.info("Unit found: " + unitOptional.get());
        } else {
            logger.warning("Unit not found for id: " + unitId);
        }
        return unitOptional.orElse(null);
    }

    public Subresidence updateUnit(Long unitId, Subresidence updatedUnit) throws IOException {
        Optional<Subresidence> unitOptional = unitRepository.findById(unitId);
        if (unitOptional.isPresent()) {
            Subresidence existingUnit = unitOptional.get();

            // Copy the updated image data to the existing unit
            existingUnit.setImg(updatedUnit.getImg());

            logger.info("Unit updated: " + existingUnit);
            return unitRepository.save(existingUnit);
        } else {
            logger.warning("Unit not found for id: " + unitId);
            throw new IOException("Unit not found for id: " + unitId);
        }
    }

    public boolean deleteUnit(Long unitId) {
        Optional<Subresidence> unitOptional = unitRepository.findById(unitId);
        if (unitOptional.isPresent()) {
            unitRepository.delete(unitOptional.get());
            logger.info("Unit deleted: " + unitOptional.get());
            return true;
        } else {
            logger.warning("Unit not found for id: " + unitId);
        }
        return false;
    }

    public List<Subresidence> getAllUnits() {
        List<Subresidence> units = unitRepository.findAll();
        logger.info("Units found: " + units.size());
        return units;
    }

    // private Unit convertToDTO(Unit unit) {
    //     Unit unitDTO = new UnitDTO();
    //     unitDTO.setUnitId(unit.getunit_id());
    //     unitDTO.setResidenceId(unit.getResidence().getResidenceId());
    //     unitDTO.setUnitNumber(unit.getUnitNumber());
    //     unitDTO.setUnitType(unit.getUnitType());
    //     unitDTO.setUnitDescription(unit.getUnitDescription());
    //     unitDTO.setMonthlyRent(unit.getMonthlyRent());
    //     return unitDTO;
    // }
}


