package com.trisul.data.access.repository;

import com.trisul.data.access.entity.IPEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPRepository extends JpaRepository<IPEntity, Long> {}
