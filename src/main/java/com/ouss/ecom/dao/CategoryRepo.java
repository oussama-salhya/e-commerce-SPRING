package com.ouss.ecom.dao;

import com.ouss.ecom.entities.Category;
import com.ouss.ecom.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {


    Category findByName(String office);
}
