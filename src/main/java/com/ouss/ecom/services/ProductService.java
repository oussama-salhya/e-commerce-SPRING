package com.ouss.ecom.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ouss.ecom.config.FileUpload;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Service
public class ProductService {
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final CompanyRepo companyRepo;
    private final Cloudinary cloudinary;
    private FileUpload fileUpload;
    public ProductService(ProductRepo productRepo, CategoryRepo categoryRepo,
                          CompanyRepo companyRepo, Cloudinary cloudinary, FileUpload fileUpload) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.companyRepo = companyRepo;
        this.cloudinary = cloudinary;
        this.fileUpload = fileUpload;
    }
    private String uploadProductImage(String image){
            try {
                return fileUpload.uploadFile(image);
            } catch (IOException e) {
                throw new CustomException.BadRequestException("Could not store the file. Error: " + e.getMessage());
            }
    }
//    private List<String> uploadProductImages(List<String> images){
//        ArrayList<String> uploadedImages = new ArrayList<>();
//        images.forEach(image ->{
//            try {
//                uploadedImages.add(fileUpload.uploadFile(image));
//            } catch (IOException e) {
//                throw new CustomException.BadRequestException("Could not store the file. Error: " + e.getMessage());
//            }
//        });
//        return uploadedImages;
//    }
    public Product createProduct(Product product ) {
        System.out.println(product.getId());
        AppUser user = SecurityUtil.getAuthenticatedUser();
        product.setUser(user);
        System.out.println("1");
        if (product.getImage() != null && !product.getImage().isEmpty()) {
        System.out.println("2");
            String uploadResult = uploadProductImage(product.getImage());
        System.out.println("3");
            System.out.println(uploadResult);
            product.setImage(uploadResult);
        }
        // Fetch the Category and Company entities from the database
        Category category = categoryRepo.findByName(product.getCategory().getName());
        Company company = companyRepo.findByName(product.getCompany().getName());

        // see if company and category are null
        if (category == null) {
            throw new CustomException.NotFoundException("No category with name : " + product.getCategory().getName());
        }
        if (company == null) {
            throw new CustomException.NotFoundException("No company with name : " + product.getCompany().getName());
        }
        // Set the fetched entities to the product
        product.setCategory(category);
        product.setCompany(company);
        return productRepo.save(product);
    }

    public Page<Product> getAllProducts(Specification<Product> spec, Pageable pageable) {
        return productRepo.findAll(spec, pageable);
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
        // Fetch the Category and Company entities from the database
        Category category = categoryRepo.findByName(product.getCategory().getName());
        Company company = companyRepo.findByName(product.getCompany().getName());

        // see if company and category are null
        if (category == null) {
            throw new CustomException.NotFoundException("No category with name : " + product.getCategory().getName());
        }
        if (company == null) {
            throw new CustomException.NotFoundException("No company with name : " + product.getCompany().getName());
        }
        // Set the fetched entities to the product
        product.setCategory(category);
        product.setCompany(company);
        return productRepo.save(product);
    }

    public void deleteProduct(Long id) {
        Optional<Product> existingProduct = productRepo.findById(id);
        if (!existingProduct.isPresent()) {
            throw new CustomException.NotFoundException("No product with id : " + id);
        }
        productRepo.deleteById(id);
    }


}