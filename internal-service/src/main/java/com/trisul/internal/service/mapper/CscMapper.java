package com.trisul.internal.service.mapper;

import com.trisul.data.access.entity.CityEntity;
import com.trisul.data.access.entity.CountryEntity;
import com.trisul.data.access.entity.StateEntity;
import com.trisul.internal.service.model.CscDetail;
import com.trisul.internal.service.model.CscTypeEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CscMapper {

  @Mapping(source = "ce.countryID", target = "cscID")
  @Mapping(source = "ce.countryKey", target = "cscKey")
  @Mapping(source = "ce.countryShortDescription", target = "cscShortDescription")
  @Mapping(source = "ce.countryDescription", target = "cscDescription")
  @Mapping(source = "ce.countryPriority", target = "cscPriority")
  @Mapping(source = "ce.countryPhoneCode", target = "cscCodeID")
  @Mapping(source = "ce.countryKey", target = "cscType", qualifiedByName = "CSC_COUNTRY_TYPE")
  CscDetail convertCountryEntityToCscDetail(CountryEntity ce);

  @Mapping(source = "se.stateID", target = "cscID")
  @Mapping(source = "se.stateKey", target = "cscKey")
  @Mapping(source = "se.stateShortDescription", target = "cscShortDescription")
  @Mapping(source = "se.stateDescription", target = "cscDescription")
  @Mapping(source = "se.statePriority", target = "cscPriority")
  @Mapping(source = "se.stateCountryId", target = "cscCodeID")
  @Mapping(source = "se.stateKey", target = "cscType", qualifiedByName = "CSC_STATE_TYPE")
  CscDetail convertStateEntityToCscDetail(StateEntity se);

  @Mapping(source = "ce.cityID", target = "cscID")
  @Mapping(source = "ce.cityKey", target = "cscKey")
  @Mapping(source = "ce.cityShortDescription", target = "cscShortDescription")
  @Mapping(source = "ce.cityDescription", target = "cscDescription")
  @Mapping(source = "ce.cityPriority", target = "cscPriority")
  @Mapping(source = "ce.cityStateId", target = "cscCodeID")
  @Mapping(source = "ce.cityKey", target = "cscType", qualifiedByName = "CSC_CITY_TYPE")
  CscDetail convertCityEntityToCscDetail(CityEntity ce);

  @Named("CSC_COUNTRY_TYPE")
  default CscTypeEnum toCscCountryTypeEnum(String source) {
    return CscTypeEnum.COUNTRY;
  }

  @Named("CSC_STATE_TYPE")
  default CscTypeEnum toCscStateTypeEnum(String source) {
    return CscTypeEnum.STATE;
  }

  @Named("CSC_CITY_TYPE")
  default CscTypeEnum toCscCityTypeEnum(String source) {
    return CscTypeEnum.CITY;
  }

  @Mapping(source = "cd.cscID", target = "countryID")
  @Mapping(source = "cd.cscKey", target = "countryKey")
  @Mapping(source = "cd.cscShortDescription", target = "countryShortDescription")
  @Mapping(source = "cd.cscDescription", target = "countryDescription")
  @Mapping(source = "cd.cscPriority", target = "countryPriority")
  @Mapping(source = "cd.cscCodeID", target = "countryPhoneCode")
  CountryEntity convertCscDetailToCountryEntity(CscDetail cd);

  @Mapping(source = "cd.cscID", target = "stateID")
  @Mapping(source = "cd.cscKey", target = "stateKey")
  @Mapping(source = "cd.cscShortDescription", target = "stateShortDescription")
  @Mapping(source = "cd.cscDescription", target = "stateDescription")
  @Mapping(source = "cd.cscPriority", target = "statePriority")
  @Mapping(source = "cd.cscCodeID", target = "stateCountryId")
  StateEntity convertCscDetailToStateEntity(CscDetail cd);

  @Mapping(source = "cd.cscID", target = "cityID")
  @Mapping(source = "cd.cscKey", target = "cityKey")
  @Mapping(source = "cd.cscShortDescription", target = "cityShortDescription")
  @Mapping(source = "cd.cscDescription", target = "cityDescription")
  @Mapping(source = "cd.cscPriority", target = "cityPriority")
  @Mapping(source = "cd.cscCodeID", target = "cityStateId")
  CityEntity convertCscDetailToCityEntity(CscDetail cd);
}
