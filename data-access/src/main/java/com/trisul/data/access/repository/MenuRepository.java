package com.trisul.data.access.repository;

import com.trisul.data.access.entity.MenuEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Long> {

  List<MenuEntity> findAllByMenuIsAuthReqOrderByMenuPriorityAsc(Boolean authStatus);
}
