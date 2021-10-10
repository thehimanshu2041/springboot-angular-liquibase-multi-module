package com.trisul.data.access.dao;

import com.trisul.data.access.entity.*;
import java.util.List;

public interface StaticDataDao {

  CodeTypeEntity getCodeTypeEntityByCodeType(String codeType);

  CodeEntity getCodeEntityByCodeId(Long codeId);

  CodeEntity getCodeEntityByCodeKey(String codeKey);

  List<TileEntity> getConfiguredTile(Boolean tileIsAccessed, Boolean tileIsDeleted);

  CountryEntity getCountryByCountryKey(String countryKey);

  List<CountryEntity> getCountries();

  List<StateEntity> getStatesByCountryId(Long countryId);

  List<CityEntity> getCitiesByStateId(Long stateId);

  CountryEntity getCountryByCountryId(Long countryId);

  StateEntity getStateByStateId(Long stateId);

  CityEntity getCityByCityId(Long cityId);

  StateEntity getStateByStateKey(String stateKey);

  CityEntity getCityByCityKey(String cityKey);
}
