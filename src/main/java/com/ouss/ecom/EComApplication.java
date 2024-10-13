package com.ouss.ecom;

import com.ouss.ecom.auth.AuthenticationService;
import com.ouss.ecom.config.RateLimitingFilter;
import com.ouss.ecom.dao.*;
import com.ouss.ecom.entities.*;
import com.ouss.ecom.services.ProductService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.*;

@SpringBootApplication
@RequiredArgsConstructor
@EnableScheduling
public class EComApplication {

    public static void main(String[] args) {
        SpringApplication.run(EComApplication.class, args);
    }
    @Autowired
    private UserRepo userRepo;
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
    @Autowired
    private ProductRepo productRepo;

//    @Bean
//    public FilterRegistrationBean<RateLimitingFilter> rateLimitingFilter() {
//        FilterRegistrationBean<RateLimitingFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new RateLimitingFilter());
//        registrationBean.addUrlPatterns("/api/*"); // Register filter for API endpoints
//        return registrationBean;
//    }
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
        AppUser user = new AppUser();
    if (userRepo.findByEmail("admin@gmail.com").isEmpty()){
        user.setName("admin");
        user.setEmail("admin@gmail.com");
        user.setPassword(passwordEncoder.encode("admin1232002"));
        user.setRole(roleRepo.findByRole("ADMIN"));
        userRepo.save(user);
    }
        Company company1 = new Company();
        Company company2 = new Company();
        Company company3 = new Company();
    if(companyRepo.findByName("ikea") == null){
        company1.setName("ikea");
        companyRepo.save(company1);
    }
    if(companyRepo.findByName("nike") == null){
        company2.setName("nike");
        companyRepo.save(company2);
    }
    if(companyRepo.findByName("adidas") == null){
        company3.setName("adidas");
        companyRepo.save(company3);
    }

        Category category1 = new Category();
        Category category2 = new Category();
        Category category3 = new Category();
    if(categoryRepo.findByName("classic") == null){
        category1.setName("classic");
        categoryRepo.save(category1);
    }
    if (categoryRepo.findByName("artistic") == null){
        category2.setName("artistic");
        categoryRepo.save(category2);
    }
    if(categoryRepo.findByName("boho") == null){
        category3.setName("boho");
        categoryRepo.save(category3);
    }

        // Assuming you have already set up the roles, users, companies, and categories as before

        List<String> imageSrcList = Arrays.asList(
                "https://www.tribaliste.com/20075-liste_default/small-vintage-yellow-green-boujad-rug.jpg",
                "https://www.tribaliste.com/20077-liste_default/small-vintage-red-yellow-boujad-rug.jpg",
                "https://www.tribaliste.com/20079-liste_default/small-vintage-boujad-blue-green-rug.jpg",
                "https://www.tribaliste.com/20080-liste_default/petit-tapis-vintage-boujad-vert-blanc.jpg",
                "https://www.tribaliste.com/20082-liste_default/small-vintage-beige-green-rug.jpg",
                "https://www.tribaliste.com/20085-liste_default/medium-vintage-boujad-pink-green-rug.jpg",
                "https://www.tribaliste.com/20087-liste_default/medium-vintage-boujad-mauve-yellow-rug.jpg",
                "https://www.tribaliste.com/20092-liste_default/medium-vintage-boujad-pink-yellow-rug.jpg",
                "https://www.tribaliste.com/20089-liste_default/medium-vintage-boujad-pink-black-rug.jpg",
                "https://www.tribaliste.com/20091-liste_default/medium-vintage-boujad-yellow-taupe-rug.jpg",
                "https://www.tribaliste.com/20095-liste_default/medium-vintage-boujad-red-green-rug.jpg",
                "https://www.tribaliste.com/20097-liste_default/medium-vintage-boujad-yellow-pink-rug.jpg",
                "https://www.tribaliste.com/20099-liste_default/medium-vintage-boujad-mauve-pink-rug.jpg",
                "https://www.tribaliste.com/20101-liste_default/medium-vintage-boujad-red-black-rug.jpg",
                "https://www.tribaliste.com/20103-liste_default/medium-vintage-boujad-orange-green-rug.jpg",
                "https://www.tribaliste.com/20104-liste_default/medium-vintage-boujad-pink-black-rug.jpg"
        );

        List<String> titleList = Arrays.asList(
                "Small vintage yellow green boujad rug",
                "Small vintage red yellow boujad rug",
                "Small vintage boujad blue green rug",
                "Small vintage boujad vert blanc rug",
                "Small vintage beige green rug",
                "Medium vintage boujad pink green rug",
                "Medium vintage boujad mauve yellow rug",
                "Medium vintage boujad pink yellow rug",
                "Medium vintage boujad pink black rug",
                "Medium vintage boujad yellow taupe rug",
                "Medium vintage boujad red green rug",
                "Medium vintage boujad yellow pink rug",
                "Medium vintage boujad mauve pink rug",
                "Medium vintage boujad red black rug",
                "Medium vintage boujad orange green rug",
                "Medium vintage boujad pink black rug"
        );

        List<String> descriptionList = Arrays.asList(
                "This beautiful vintage yellow and green boujad rug brings a touch of traditional Moroccan design to any home. Handwoven and crafted with intricate details, perfect for modern or classic décor.",
                "A striking combination of red and yellow, this small boujad rug showcases traditional patterns that elevate the aesthetic of your space. Woven in wool from the Middle Atlas.",
                "A stunning vintage piece in blue and green hues, this boujad rug adds a vibrant and artistic flair to any room. Carefully crafted with a nod to Moroccan heritage.",
                "Featuring a soft blend of vert and blanc, this small vintage rug is a perfect match for minimalist yet elegant interiors. Its subtle design is ideal for understated luxury.",
                "This beige and green vintage rug offers a muted, earthy palette that harmonizes well with both contemporary and traditional décor styles.",
                "A medium-sized boujad rug with lovely pink and green tones, this piece brings warmth and color to any living space. Perfect for eclectic or boho-inspired décor.",
                "The mauve and yellow hues of this vintage boujad rug create a unique visual contrast, making it a standout piece for any room seeking an infusion of bold, artistic charm.",
                "Vibrant pink and yellow hues blend together in this medium boujad rug, offering a lively and playful touch to your home’s interior design.",
                "A sophisticated blend of pink and black, this medium vintage boujad rug delivers a chic and modern look, perfect for minimalist or contemporary interiors.",
                "With its yellow and taupe tones, this medium-sized vintage boujad rug provides a neutral yet stylish option for understated elegance in any room.",
                "Red and green tones come together to form an intricate design in this medium vintage boujad rug. Its bold colors add a touch of vibrancy to any setting.",
                "The combination of yellow and pink in this medium-sized vintage rug creates a soft, playful aesthetic perfect for light, airy spaces.",
                "Soft mauve and pink tones create an inviting and warm vibe in this medium-sized boujad rug, ideal for adding a touch of coziness to any room.",
                "Classic red and black hues make this medium vintage boujad rug a timeless piece that enhances the bold, modern look of your home.",
                "The energetic orange and green tones of this vintage boujad rug infuse life and color into any room, making it a lively focal point.",
                "A chic combination of pink and black makes this medium-sized boujad rug perfect for a modern home, with a design that balances sophistication and creativity."
        );

        // Initialize a random generator
        Random random = new Random();

        // Array of categories
        Category[] categories = {category1, category2, category3};

        // Array of companies
        Company[] companies = {company1, company2, company3};

        //  Array of colors
        String[] colors = {"red", "black", "blue", "green", "yellow", "pink", "orange", "purple", "white", "brown", "gray", "beige"};
        for (int i = 0; i < imageSrcList.size(); i++) {
            // Get a random category and company
            Category category = categories[random.nextInt(categories.length)];
            Company company = companies[random.nextInt(companies.length)];
            ArrayList<String> colorList = new ArrayList<>();
            // Generate a random price between minPrice and maxPrice
            double randomPrice = 0.99 + (999.99 - 0.99) * random.nextDouble();
            BigDecimal roundedPrice = BigDecimal.valueOf(randomPrice).setScale(2, BigDecimal.ROUND_HALF_UP);

            for (int j = 0; j < 3; j++) {
                colorList.add(colors[random.nextInt(colors.length)]);
            }
            productRepo.save(
                    Product.builder()
                            .company(company)
                            .category(category)
                            .colors(colorList)
                            .description(descriptionList.get(i))
                            .name(titleList.get(i))
                            .image(imageSrcList.get(i))
                            .featured(false)
                            .price(roundedPrice.doubleValue())
                            .freeShipping(false)
                            .stock(5)
                            .user(user) // Assuming the 'admin' user adds the products
                            .build()
            );
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
