package com.ouss.ecom.dao;

import com.ouss.ecom.entities.Category;
import com.ouss.ecom.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepo extends JpaRepository<Company, String> {


    Company findByName(String ikea);
}
