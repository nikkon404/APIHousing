package com.conestoga.APIHousing.interfaces;

import com.conestoga.APIHousing.model.Residence;
import com.conestoga.APIHousing.model.Subresidence;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubresidenceRepository extends JpaRepository<Subresidence, Long> {
  List<Subresidence> findByResidence(Residence residence);

  List<Subresidence> findByUnitType(String unitType);

  List<Subresidence> findByMonthlyRentLessThan(BigDecimal rent);
}
