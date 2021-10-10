package com.trisul.internal.service.mapper;

import com.trisul.data.access.entity.MenuEntity;
import com.trisul.internal.service.model.MenuDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MenuMapper {

  MenuEntity convertMenuDetailToMenuEntity(MenuDetail md);

  MenuDetail convertMenuEntityToMenuDetail(MenuEntity me);
}
