package com.trisul.data.access.dao.impl;

import com.trisul.data.access.dao.StaticDataDao;
import com.trisul.data.access.entity.*;
import com.trisul.data.access.repository.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaticDataDaoImpl implements StaticDataDao {

  @Autowired CodeTypeRepository codeTypeRepository;

  @Autowired CodeRepository codeRepository;

  @Autowired TileRepository tileRepository;

  @Autowired CountryRepository countryRepository;

  @Autowired StateRepository stateRepository;

  @Autowired CityRepository cityRepository;

  @Override
  public CodeTypeEntity getCodeTypeEntityByCodeType(String codeType) {
    return codeTypeRepository.findByCodeTypeKey(codeType).orElse(null);
  }

  @Override
  public CodeEntity getCodeEntityByCodeId(Long codeKey) {
    return codeRepository.findByCodeID(codeKey).orElse(null);
  }

  @Override
  public CodeEntity getCodeEntityByCodeKey(String codeKey) {
    return codeRepository.findByCodeKey(codeKey).orElse(null);
  }

  @Override
  public List<TileEntity> getConfiguredTile(Boolean tileIsAccessed, Boolean tileIsDeleted) {
    return tileRepository.findAllByTileIsAccessedAndTileIsDeleteOrderByTilePriorityAsc(
        tileIsAccessed, tileIsDeleted);
  }

  @Override
  public CountryEntity getCountryByCountryKey(String countryKey) {
    return countryRepository.findByCountryKey(countryKey).orElse(null);
  }

  @Override
  public List<CountryEntity> getCountries() {
    return countryRepository.findAll();
  }

  @Override
  public List<StateEntity> getStatesByCountryId(Long countryId) {
    return stateRepository.findByStateCountryIdOrderByStatePriorityAsc(countryId);
  }

  @Override
  public List<CityEntity> getCitiesByStateId(Long stateId) {
    return cityRepository.findByCityStateIdOrderByCityPriorityAsc(stateId);
  }

  @Override
  public CountryEntity getCountryByCountryId(Long countryId) {
    return countryRepository.findByCountryID(countryId).orElse(null);
  }

  @Override
  public StateEntity getStateByStateId(Long stateId) {
    return stateRepository.findByStateID(stateId).orElse(null);
  }

  @Override
  public CityEntity getCityByCityId(Long CityId) {
    return cityRepository.findByCityID(CityId).orElse(null);
  }

  @Override
  public StateEntity getStateByStateKey(String stateKey) {
    return stateRepository.findByStateKey(stateKey).orElse(null);
  }

  @Override
  public CityEntity getCityByCityKey(String cityKey) {
    return cityRepository.findByCityKey(cityKey).orElse(null);
  }
}
