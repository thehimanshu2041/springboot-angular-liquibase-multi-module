package com.trisul.internal.service.service;

import com.trisul.internal.service.model.MenuDetail;
import java.util.List;

public interface MenuService {

  List<MenuDetail> getMenuList();

  Boolean createMenuDetail(MenuDetail menuDetail);
}
