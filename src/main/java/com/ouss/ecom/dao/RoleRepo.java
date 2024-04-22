package com.ouss.ecom.dao;

import com.ouss.ecom.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, String> {

    Role findByRole(String s);

}
