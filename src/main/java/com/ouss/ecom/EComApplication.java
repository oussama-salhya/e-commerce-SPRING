package com.ouss.ecom;

import com.ouss.ecom.auth.AuthenticationService;
import com.ouss.ecom.dao.CategoryRepo;
import com.ouss.ecom.dao.CompanyRepo;
import com.ouss.ecom.dao.RoleRepo;
import com.ouss.ecom.dao.UserRepo;
import com.ouss.ecom.entities.*;
import com.ouss.ecom.services.ProductService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Set;

@SpringBootApplication
@RequiredArgsConstructor
public class EComApplication {

    public static void main(String[] args) {
        SpringApplication.run(EComApplication.class, args);
    }
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private final AuthenticationService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private CompanyRepo companyRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostConstruct
    public void init(){

    if(roleRepo.findByRole("ADMIN") == null){
        Role role = Role.builder()
                .role("ADMIN")
                .autorities(Set.of(Role.Authoritie.CREATE, Role.Authoritie.READ, Role.Authoritie.UPDATE, Role.Authoritie.DELETE))
                .build();
        roleRepo.save(role);
    }
    if(roleRepo.findByRole("USER") == null){
        Role role = Role.builder()
                .role("USER")
                .autorities(Set.of(Role.Authoritie.CREATE, Role.Authoritie.READ, Role.Authoritie.UPDATE, Role.Authoritie.DELETE))
                .build();
        roleRepo.save(role);
    }
    if (userRepo.findByEmail("admin@gmail.com").isEmpty()){
        AppUser user = new AppUser();
        user.setName("admin");
        user.setEmail("admin@gmail.com");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setRole(roleRepo.findByRole("ADMIN"));
        userService.register(user);
    }
    if(companyRepo.findByName("IKEA") == null){
        Company company = new Company();
        company.setName("IKEA");
        companyRepo.save(company);
    }
    if (categoryRepo.findByName("OFFICE") == null){
        Category category = new Category();
        category.setName("OFFICE");
        categoryRepo.save(category);
    }
    if(companyRepo.findByName("NIKE") == null){
        Company company = new Company();
        company.setName("NIKE");
        companyRepo.save(company);
    }
    if (categoryRepo.findByName("SPORT") == null){
        Category category = new Category();
        category.setName("SPORT");
        categoryRepo.save(category);
    }

//        Role role = Role.builder()
//                .role("ADMIN")
//                .autorities(Set.of(Role.Authoritie.CREATE, Role.Authoritie.READ, Role.Authoritie.UPDATE, Role.Authoritie.DELETE))
//                .build();
//        roleRepo.save(role);
//
//        Role role2 = Role.builder()
//                .role("MANAGER")
//                .autorities(Set.of(Role.Authoritie.CREATE, Role.Authoritie.READ, Role.Authoritie.UPDATE))
//                .build();
//        roleRepo.save(role2);
//        Role role3 = Role.builder()
//                .role("USER")
//                .autorities(Set.of(Role.Authoritie.READ))
//                .build();
//        roleRepo.save(role3);

//        AppUser user = new AppUser();
//        user.setEmail("testUser");
//        user.setPassword("testPassword");
//
//        AppUser createdUser = userRepo.save(user);
//        System.out.println("Created user: " + createdUser);
//
//
//        Product product = new Product();
//        product.setName("Test Product");
//        product.setPrice(99.99);
//        product.setDescription("This is a test product");
//        product.setCategory(Product.Category.OFFICE);
//        product.setCompany(Product.Company.IKEA);
//        product.setColors(Arrays.asList("Red", "Blue"));
//        product.setInventory(10);
//        product.getUser().setId(createdUser.getId());
//
//        Product createdProduct = productService.createProduct(product);
//        System.out.println("Created product: " + createdProduct);
    }
}
