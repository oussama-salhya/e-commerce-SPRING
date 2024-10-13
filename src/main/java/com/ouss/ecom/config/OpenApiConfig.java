package com.ouss.ecom.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Oussama SALHYA",
                        url = "https://www.oussama-salhya.me/",
                        email = "oussamasalhya@gmail.com"
                ),
                title = "OpenAPI E-commerce ",
                version = "1.0",
                description = """
                        E-Commerce API Documentation \n
                        Welcome to the Comfy Shop E-Commerce API! This API is designed to enhance your e-commerce platform by providing a secure and efficient way to manage online shopping activities. \n
                        It offers a range of features for users, with specific roles and permissions to ensure a smooth and secure user experience.
                        API Summary \n
                        The Comfy Shop E-Commerce API, built with the Spring framework, offers a variety of endpoints for: \n
                        •\tProduct Browsing: Users can view detailed product information, search for products, and explore categories and companies. \n
                        •\tOrder Management: Users can place orders, view order history, and track order status and and process payments. \n
                        •\tCustomer Account Management: Users can register, log in, and manage their account settings and profiles. \n
                        •\tInventory Viewing: Users can check product availability and stock levels. \n
                        •\tProduct Management: Create, update, delete, and retrieve product details. (for Admin Only) \n
                        •\tCustomer Management: Access customer information and manage customer accounts. . (for Admin Only) \n
                        •\tProduct Reviews: Users can add reviews to products, helping other customers with feedback and ratings. \n

                        Getting Started \n
                        To start using the Comfy Shop E-Commerce API, follow these steps: \n 
                        1.\tUser Registration and Login: (Important step, you must login !!!!!!!!!!!!!! ) \n
                        o\tRegister: New users must register to access the API. Registration creates a user account with a USER role. \n
                        o\tLogin: After registration, log in to receive an authentication token. This token must be included in the header of each API request to authorize access. \n \n
                        2.\tExplore User Capabilities: \n
                        o\tAs a USER, you can browse products, place and manage orders, and update your account settings. \n
                        o\tNote that USERS cannot perform admin-specific actions such as creating, updating, or deleting products. These actions are restricted to administrators. \n \n
                        4.\tIntegrate with Your Platform: Utilize API calls to enhance your application’s functionality, enabling users to engage with your storefront seamlessly. \n \n
                        3.\tMonitor and Communicate: Utilize available endpoints to provide users with updates on their orders and account activities. \n \n
                        - you can use this API to build your own e-commerce platform, or integrate it with an existing platform to enhance user experience and streamline shopping activities. by using this link \n 
                        https://comfy-shop-7p8v.onrender.com/api/v1/ as a base URL for the API. \n
                        for example, to get all products you can use this link https://comfy-shop-7p8v.onrender.com/api/v1/products but you need to be already register and login by using this endpoint (with post method) :\n
                        https://comfy-shop-7p8v.onrender.com/api/v1/auth/register or https://comfy-shop-7p8v.onrender.com/api/v1/auth/login \n \n
                        """
        )
)

public class OpenApiConfig {
}
