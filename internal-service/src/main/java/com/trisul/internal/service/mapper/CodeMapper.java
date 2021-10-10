package com.trisul.internal.service.mapper;

import com.trisul.data.access.entity.CodeEntity;
import com.trisul.data.access.entity.CodeTypeEntity;
import com.trisul.internal.service.model.CodeDetail;
import com.trisul.internal.service.model.CodeTypeDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CodeMapper {

  @Mapping(target = "codeType", ignore = true)
  CodeEntity convertCodeDetailToCodeEntity(CodeDetail cd);

  CodeTypeEntity convertCodeTypeDetailToCodeTypeEntity(CodeTypeDetail ctd);

  @Mapping(source = "ce.codeType.codeTypeKey", target = "codeType")
  CodeDetail convertCodeEntityToCodeDetail(CodeEntity ce);

  CodeTypeDetail convertCodeTypeEntityToCodeTypeDetail(CodeTypeEntity cte);
}
