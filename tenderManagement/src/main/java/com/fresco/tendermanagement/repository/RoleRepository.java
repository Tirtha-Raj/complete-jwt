package com.fresco.tendermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fresco.tendermanagement.model.RoleModel;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Integer> {

}
