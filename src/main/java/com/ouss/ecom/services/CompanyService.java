package com.ouss.ecom.services;

import com.ouss.ecom.dao.CompanyRepo;
import com.ouss.ecom.entities.Company;
import com.ouss.ecom.errors.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepo companyRepository;

    public Company createCompany(Company company) {
        if (companyRepository.findByName(company.getName()) != null) {
            throw new CustomException.BadRequestException("Company already exists");
        }
        return companyRepository.save(company);
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getSingleCompany(Long id) {
        Optional<Company> company = companyRepository.findById(id);
        return company.orElseThrow(() -> new CustomException.NotFoundException("Company not found"));
    }

    public Company updateCompany(Long id, Company company) {
        Company existingCompany = getSingleCompany(id);
        existingCompany.setName(company.getName());
        return companyRepository.save(existingCompany);
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }
}