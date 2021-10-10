package com.trisul.internal.service.service.impl;

import com.trisul.internal.service.model.CodeDetail;
import com.trisul.internal.service.model.CscDetail;
import com.trisul.internal.service.model.CscTypeEnum;
import com.trisul.internal.service.service.ResolverService;
import com.trisul.internal.service.service.StaticDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResolverServiceImpl implements ResolverService {

  @Autowired StaticDataService staticDataService;

  @Override
  public CodeDetail resolveCodeDetail(CodeDetail codeDetail) {
    if (null != codeDetail.getCodeID()) {
      return staticDataService.getCodeByCodeId(codeDetail.getCodeID());
    }
    return staticDataService.getCodeByCodeKey(codeDetail.getCodeKey());
  }

  @Override
  public CscDetail resolveCscDetail(CscDetail cscDetail) {
    if (null != cscDetail.getCscID()) {
      return getCscById(cscDetail.getCscID(), cscDetail.getCscType());
    }
    return getCscByCode(cscDetail.getCscKey(), cscDetail.getCscType());
  }

  private CscDetail getCscById(Long id, CscTypeEnum cscTypeEnum) {
    if (cscTypeEnum.CITY.getCode().equals("CITY")) {
      return staticDataService.getCityByCityId(id);
    } else if (cscTypeEnum.STATE.getCode().equals("STATE")) {
      return staticDataService.getStateByStateId(id);
    } else {
      return staticDataService.getCountryByCountryId(id);
    }
  }

  private CscDetail getCscByCode(String code, CscTypeEnum cscTypeEnum) {
    if (cscTypeEnum.getCode().equals("CITY")) {
      return staticDataService.getCityByCityKey(code);
    } else if (cscTypeEnum.getCode().equals("STATE")) {
      return staticDataService.getStateByStateKey(code);
    } else {
      return staticDataService.getCountryByCountryKey(code);
    }
  }
}
