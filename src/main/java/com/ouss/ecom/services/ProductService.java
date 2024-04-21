package com.ouss.ecom.services;

import com.ouss.ecom.dao.CategoryRepo;
import com.ouss.ecom.dao.CompanyRepo;
import com.ouss.ecom.dao.ProductRepo;
import com.ouss.ecom.dao.ProductRepo;
import com.ouss.ecom.entities.AppUser;
import com.ouss.ecom.entities.Category;
import com.ouss.ecom.entities.Company;
import com.ouss.ecom.entities.Product;
import com.ouss.ecom.errors.CustomException;
import com.ouss.ecom.utils.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final CompanyRepo companyRepo;

    public ProductService(ProductRepo productRepo, CategoryRepo categoryRepo, CompanyRepo companyRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.companyRepo = companyRepo;
    }
    public Product createProduct(Product product) {
        // Add your business logic here
        AppUser user = SecurityUtil.getAuthenticatedUser();
        product.setUser(user);
        // Fetch the Category and Company entities from the database
        Category category = categoryRepo.findByName(product.getCategory().getName());
        Company company = companyRepo.findByName(product.getCompany().getName());

        // Set the fetched entities to the product
        product.setCategory(category);
        product.setCompany(company);
        return productRepo.save(product);
    }

    public List<Product> getAllProducts() {
        // Add your business logic here
        List<Product> products = productRepo.findAll();
        return products;
    }

    public Product getSingleProduct(Long id) {
        // Add your business logic here
        Optional<Product> product = productRepo.findById(id);
        if (!product.isPresent()) {
            throw new CustomException.NotFoundException("No product with id : " + id);
        }
        return product.get();
    }

    public Product updateProduct(Product product, Long id) {
        Optional<Product> existingProduct = productRepo.findById(id);
        if (!existingProduct.isPresent()) {
            throw new CustomException.NotFoundException("No product with id : " + product.getId());
        }
        return productRepo.save(product);
    }

    public void deleteProduct(Long id) {
        Optional<Product> existingProduct = productRepo.findById(id);
        if (!existingProduct.isPresent()) {
            throw new CustomException.NotFoundException("No product with id : " + id);
        }
        productRepo.deleteById(id);
    }

    public String uploadImage(MultipartFile image) {
        // Add your business logic for handling file uploads here
        // This is a placeholder and needs to be implemented according to your application's requirements
        return null;
    }
}