package com.ouss.ecom.services;

import com.ouss.ecom.dao.ProductRepo;
import com.ouss.ecom.dao.ProductRepo;
import com.ouss.ecom.entities.AppUser;
import com.ouss.ecom.entities.Product;
import com.ouss.ecom.errors.CustomException;
import com.ouss.ecom.utils.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepo ProductRepo;

    public ProductService(ProductRepo ProductRepo) {
        this.ProductRepo = ProductRepo;
    }

    public Product createProduct(Product product) {
        // Add your business logic here
        AppUser user = SecurityUtil.getAuthenticatedUser();
        product.setUser(user);
        return ProductRepo.save(product);
    }

    public List<Product> getAllProducts() {
        // Add your business logic here
        return ProductRepo.findAll();
    }

    public Product getSingleProduct(Long id) {
        // Add your business logic here
        Optional<Product> product = ProductRepo.findById(id);
        if (!product.isPresent()) {
            throw new CustomException.NotFoundException("No product with id : " + id);
        }
        return product.get();
    }

    public Product updateProduct(Long id, Product product) {
        // Add your business logic here
        Optional<Product> existingProduct = ProductRepo.findById(id);
        if (!existingProduct.isPresent()) {
            throw new CustomException.NotFoundException("No product with id : " + id);
        }
        product.setId(id);
        return ProductRepo.save(product);
    }

    public void deleteProduct(Long id) {
        // Add your business logic here
        Optional<Product> existingProduct = ProductRepo.findById(id);
        if (!existingProduct.isPresent()) {
            throw new CustomException.NotFoundException("No product with id : " + id);
        }
        ProductRepo.deleteById(id);
    }

    public String uploadImage(MultipartFile image) {
        // Add your business logic for handling file uploads here
        // This is a placeholder and needs to be implemented according to your application's requirements
        return null;
    }
}