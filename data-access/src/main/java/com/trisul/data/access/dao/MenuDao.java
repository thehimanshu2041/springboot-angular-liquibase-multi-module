package com.trisul.data.access.dao;

import com.trisul.data.access.entity.MenuEntity;
import java.util.List;

public interface MenuDao {

  List<MenuEntity> getMenuList(Boolean status);

  MenuEntity createMenuDetail(MenuEntity menuEntity);
}
