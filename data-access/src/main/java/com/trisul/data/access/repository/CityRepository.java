package com.trisul.data.access.repository;

import com.trisul.data.access.entity.CityEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Long> {

  List<CityEntity> findByCityStateIdOrderByCityPriorityAsc(Long countryId);

  Optional<CityEntity> findByCityID(Long cityId);

  Optional<CityEntity> findByCityKey(String cityKey);
}
