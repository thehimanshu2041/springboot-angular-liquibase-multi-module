package com.trisul.internal.service.mapper;

import com.trisul.data.access.entity.*;
import com.trisul.internal.service.model.UserDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {CodeMapper.class, CscMapper.class})
public interface UserMapper {

  @Mapping(source = "ud.title.codeID", target = "title")
  @Mapping(source = "ud.gender.codeID", target = "gender")
  @Mapping(source = "ud.addressDetail", target = "addressEntity")
  @Mapping(source = "ud.cardDetail", target = "cardEntity")
  @Mapping(source = "ud.addressDetail.addressCity.cscID", target = "addressEntity.addressCity")
  @Mapping(source = "ud.addressDetail.addressState.cscID", target = "addressEntity.addressState")
  @Mapping(
      source = "ud.addressDetail.addressCountry.cscID",
      target = "addressEntity.addressCountry")
  UserEntity convertUserDetailToUserEntity(UserDetail ud);

  @Mapping(source = "ue.addressEntity.addressID", target = "addressDetail.addressID")
  @Mapping(source = "ue.addressEntity.addressAddress1", target = "addressDetail.addressAddress1")
  @Mapping(source = "ue.addressEntity.addressAddress2", target = "addressDetail.addressAddress2")
  @Mapping(source = "ue.addressEntity.addressAddress3", target = "addressDetail.addressAddress3")
  @Mapping(source = "ue.cardEntity", target = "cardDetail")
  @Mapping(source = "titleCode", target = "title")
  @Mapping(source = "genderCode", target = "gender")
  @Mapping(source = "cte", target = "addressDetail.addressCity")
  @Mapping(source = "ste", target = "addressDetail.addressState")
  @Mapping(source = "cne", target = "addressDetail.addressCountry")
  UserDetail convertUserEntityToUserDetail(
      UserEntity ue,
      CodeEntity titleCode,
      CodeEntity genderCode,
      CityEntity cte,
      StateEntity ste,
      CountryEntity cne);
}
