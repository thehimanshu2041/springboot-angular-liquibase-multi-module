package com.trisul.internal.service.service.impl;

import com.trisul.data.access.dao.StaticDataDao;
import com.trisul.data.access.entity.CodeTypeEntity;
import com.trisul.data.access.entity.TileEntity;
import com.trisul.internal.service.mapper.CodeMapper;
import com.trisul.internal.service.mapper.CscMapper;
import com.trisul.internal.service.model.CodeDetail;
import com.trisul.internal.service.model.CscDetail;
import com.trisul.internal.service.model.ManagedContent;
import com.trisul.internal.service.model.ManagedContentTile;
import com.trisul.internal.service.service.StaticDataService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaticDataServiceImpl implements StaticDataService {

  @Autowired StaticDataDao staticDataDao;

  @Autowired CodeMapper codeMapper;

  @Autowired CscMapper cscMapper;

  @Override
  public CodeDetail getCodeByCodeId(Long codeId) {
    return codeMapper.convertCodeEntityToCodeDetail(staticDataDao.getCodeEntityByCodeId(codeId));
  }

  @Override
  public CodeDetail getCodeByCodeKey(String codeKey) {
    return codeMapper.convertCodeEntityToCodeDetail(staticDataDao.getCodeEntityByCodeKey(codeKey));
  }

  @Override
  public List<CodeDetail> getCodesByCodeType(String codeType) {
    CodeTypeEntity codeTypeEntity = staticDataDao.getCodeTypeEntityByCodeType(codeType);
    if (null == codeTypeEntity) {
      return Collections.EMPTY_LIST;
    }
    return codeTypeEntity.getCodes().stream()
        .map(m -> codeMapper.convertCodeEntityToCodeDetail(m))
        .collect(Collectors.toList());
  }

  @Override
  public List<ManagedContent> getConfiguredTiles() {
    return createTileStructure(staticDataDao.getConfiguredTile(true, false));
  }

  @Override
  public List<CscDetail> getCountries() {
    return staticDataDao.getCountries().stream()
        .map(m -> cscMapper.convertCountryEntityToCscDetail(m))
        .collect(Collectors.toList());
  }

  @Override
  public CscDetail getCountryByCountryId(Long countryId) {
    return cscMapper.convertCountryEntityToCscDetail(
        staticDataDao.getCountryByCountryId(countryId));
  }

  @Override
  public CscDetail getCountryByCountryKey(String countryKey) {
    return cscMapper.convertCountryEntityToCscDetail(
        staticDataDao.getCountryByCountryKey(countryKey));
  }

  @Override
  public List<CscDetail> getStatesByCountryId(Long countryId) {
    return staticDataDao.getStatesByCountryId(countryId).stream()
        .map(m -> cscMapper.convertStateEntityToCscDetail(m))
        .collect(Collectors.toList());
  }

  @Override
  public CscDetail getStateByStateId(Long stateId) {
    return cscMapper.convertStateEntityToCscDetail(staticDataDao.getStateByStateId(stateId));
  }

  @Override
  public CscDetail getStateByStateKey(String stateKey) {
    return cscMapper.convertStateEntityToCscDetail(staticDataDao.getStateByStateKey(stateKey));
  }

  @Override
  public List<CscDetail> getCitiesByStateId(Long stateId) {
    return staticDataDao.getCitiesByStateId(stateId).stream()
        .map(m -> cscMapper.convertCityEntityToCscDetail(m))
        .collect(Collectors.toList());
  }

  @Override
  public CscDetail getCityByCityId(Long CityId) {
    return cscMapper.convertCityEntityToCscDetail(staticDataDao.getCityByCityId(CityId));
  }

  @Override
  public CscDetail getCityByCityKey(String cityKey) {
    return cscMapper.convertCityEntityToCscDetail(staticDataDao.getCityByCityKey(cityKey));
  }

  private List<ManagedContent> createTileStructure(List<TileEntity> list) {
    List<ManagedContent> managedContentList = new ArrayList<>();
    ManagedContent manageContent = null;
    ManagedContentTile managedContentTile = null;
    for (TileEntity mc : list) {
      managedContentTile = new ManagedContentTile();
      manageContent = new ManagedContent();
      managedContentTile.setKey(mc.getTileKey());
      managedContentTile.setDescription(mc.getTileDescription());
      managedContentTile.setTitle(mc.getTileDescription());
      managedContentTile.setDelete(mc.getTileIsDelete());
      managedContentTile.setAccessed(mc.getTileIsAccessed());
      managedContentTile.setIcon(mc.getTileIcon());
      managedContentTile.setType(mc.getTileType());
      manageContent.setTile(managedContentTile);
      managedContentList.add(manageContent);
    }
    return managedContentList;
  }
}
