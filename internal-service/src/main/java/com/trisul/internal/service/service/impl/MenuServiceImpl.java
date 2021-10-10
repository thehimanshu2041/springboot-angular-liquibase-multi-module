package com.trisul.internal.service.service.impl;

import com.trisul.core.security.user.UserStore;
import com.trisul.data.access.dao.MenuDao;
import com.trisul.data.access.dao.UserDao;
import com.trisul.data.access.entity.MenuEntity;
import com.trisul.internal.service.mapper.MenuMapper;
import com.trisul.internal.service.model.MenuDetail;
import com.trisul.internal.service.service.MenuService;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {

  @Autowired MenuDao menuDao;

  @Autowired MenuMapper menuMapper;

  @Autowired UserStore userStore;

  @Autowired UserDao userDao;

  public List<MenuDetail> getMenuList() {
    List<MenuEntity> finalMenuEntities = new ArrayList<>();
    Map<Long, MenuEntity> mapTmp = new HashMap<>();
    List<MenuEntity> menuEntityList =
        menuDao.getMenuList(null != userDao.findByUsername(userStore.getLoggedInUser()));
    for (MenuEntity current : menuEntityList) {
      mapTmp.put(current.getMenuID(), current);
    }

    for (MenuEntity current : menuEntityList) {
      Long parentId = current.getMenuParentID();

      if (parentId != current.getMenuID()) {
        MenuEntity parent = mapTmp.get(parentId);
        if (parent != null) {
          current.setParent(parent);
          parent.addChild(current);
          mapTmp.put(parentId, parent);
          mapTmp.put(current.getMenuID(), current);
        }
      }
    }

    for (MenuEntity node : mapTmp.values()) {
      if (null == node.getParent()) {
        finalMenuEntities.add(node);
      }
    }

    List<MenuDetail> finalMenuDetail =
        finalMenuEntities.stream()
            .map(m -> menuMapper.convertMenuEntityToMenuDetail(m))
            .collect(Collectors.toList());
    finalMenuDetail.sort(Comparator.comparing(MenuDetail::getMenuPriority));
    return finalMenuDetail;
  }

  @Override
  public Boolean createMenuDetail(MenuDetail menuDetail) {
    return null != menuDao.createMenuDetail(menuMapper.convertMenuDetailToMenuEntity(menuDetail));
  }

  /*private static final Cache<Long, List<MenuDetail>> menuCacheBuilder =
    CacheBuilder.newBuilder().initialCapacity(10).expireAfterAccess(10, TimeUnit.MINUTES).build();
  public List<MenuDetail> getMenuList() {
    List<MenuDetail> menuCache = menuCacheBuilder.getIfPresent(1000000001L);
    if (menuCache != null) {
      return menuCache;
    }
    menuCache = getMappedMenuList(status);
    menuCacheBuilder.put(1000000001L, menuCache);
    return menuCache;
  }*/
}
