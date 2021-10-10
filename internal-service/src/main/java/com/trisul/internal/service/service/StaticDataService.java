package com.trisul.internal.service.service;

import com.trisul.internal.service.model.CodeDetail;
import com.trisul.internal.service.model.CscDetail;
import com.trisul.internal.service.model.ManagedContent;
import java.util.List;

public interface StaticDataService {

  CodeDetail getCodeByCodeId(Long codeId);

  CodeDetail getCodeByCodeKey(String codeKey);

  List<CodeDetail> getCodesByCodeType(String codeType);

  List<ManagedContent> getConfiguredTiles();

  List<CscDetail> getCountries();

  CscDetail getCountryByCountryId(Long countryId);

  CscDetail getCountryByCountryKey(String countryKey);

  List<CscDetail> getStatesByCountryId(Long countryId);

  CscDetail getStateByStateId(Long stateId);

  CscDetail getStateByStateKey(String stateKey);

  List<CscDetail> getCitiesByStateId(Long stateId);

  CscDetail getCityByCityId(Long cityId);

  CscDetail getCityByCityKey(String cityKey);
}
