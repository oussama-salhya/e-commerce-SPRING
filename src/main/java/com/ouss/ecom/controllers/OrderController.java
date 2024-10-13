package com.ouss.ecom.controllers;

import com.ouss.ecom.dao.ProductRepo;
import com.ouss.ecom.dto.OrderDTO;
import com.ouss.ecom.dto.ProductDTO;
import com.ouss.ecom.entities.Order;
import com.ouss.ecom.errors.CustomException;
import com.ouss.ecom.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final ProductRepo productRepo;

    public OrderController(OrderService orderService, ProductRepo productRepo) {
        this.orderService = orderService;
        this.productRepo = productRepo;
    }

    @Operation(
            summary = "Create an order",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Order object that needs to be created",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\n" +
                                            "    \"orderItems\": [\n" +
                                            "        {\n" +
                                            "            \"productId\" : 1,\n" +
                                            "            \"amount\" : 2\n" +
                                            "        },\n" +
                                            "        {\n" +
                                            "            \"productId\" : 2,\n" +
                                            "            \"amount\" : 2\n" +
                                            "        }\n" +
                                            "    ],\n" +
                                            "    \"tax\": 10.0,\n" +
                                            "    \"shippingFee\": 5.0\n" +
                                            "}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Order created",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "{\n" +
                                                    "    \"message\": \"Order created\",\n" +
                                                    "    \"status\": 201\n" +
                                                    "}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "{\n" +
                                                    "    \"message\": \"Invalid input\",\n" +
                                                    "    \"status\": 400\n" +
                                                    "}"
                                    )
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO order) {
        Order createdOrder = orderService.createOrder(order);
        return new ResponseEntity<>(orderService.toOrderDTO(createdOrder), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all orders",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of orders",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "[\n" +
                                                    "    {\n" +
                                                    "        \"user\": {\n" +
                                                    "            \"name\": \"admin\",\n" +
                                                    "            \"email\": \"admin@gmail.com\",\n" +
                                                    "            \"role\": \"ADMIN\"\n" +
                                                    "        },\n" +
                                                    "        \"tax\": 10.0,\n" +
                                                    "        \"shippingFee\": 5.0,\n" +
                                                    "        \"status\": \"PENDING\",\n" +
                                                    "        \"orderItems\": [\n" +
                                                    "            {\n" +
                                                    "                \"amount\": 2,\n" +
                                                    "                \"productId\": 1,\n" +
                                                    "                \"productDTO\": {\n" +
                                                    "                    \"id\": 1,\n" +
                                                    "                    \"name\": \"Sample Product\",\n" +
                                                    "                    \"price\": 29.99,\n" +
                                                    "                    \"description\": \"This is a sample product description.\",\n" +
                                                    "                    \"image\": null,\n" +
                                                    "                    \"category\": {\n" +
                                                    "                        \"id\": 1,\n" +
                                                    "                        \"name\": \"office\"\n" +
                                                    "                    },\n" +
                                                    "                    \"company\": {\n" +
                                                    "                        \"id\": 2,\n" +
                                                    "                        \"name\": \"nike\"\n" +
                                                    "                    },\n" +
                                                    "                    \"colors\": [\n" +
                                                    "                        \"Red\",\n" +
                                                    "                        \"Blue\",\n" +
                                                    "                        \"Green\"\n" +
                                                    "                    ],\n" +
                                                    "                    \"featured\": true,\n" +
                                                    "                    \"freeShipping\": false,\n" +
                                                    "                    \"stock\": 50,\n" +
                                                    "                    \"averageRating\": 4.5,\n" +
                                                    "                    \"numOfReviews\": 10,\n" +
                                                    "                    \"user\": {\n" +
                                                    "                        \"name\": \"admin\",\n" +
                                                    "                        \"email\": \"admin@gmail.com\",\n" +
                                                    "                        \"role\": \"ADMIN\"\n" +
                                                    "                    },\n" +
                                                    "                    \"reviews\": null\n" +
                                                    "                }\n" +
                                                    "            },\n" +
                                                    "            {\n" +
                                                    "                \"amount\": 2,\n" +
                                                    "                \"productId\": 2,\n" +
                                                    "                \"productDTO\": {\n" +
                                                    "                    \"id\": 2,\n" +
                                                    "                    \"name\": \"Sample Product\",\n" +
                                                    "                    \"price\": 29.99,\n" +
                                                    "                    \"description\": \"This is a sample product description.\",\n" +
                                                    "                    \"image\": null,\n" +
                                                    "                    \"category\": {\n" +
                                                    "                        \"id\": 1,\n" +
                                                    "                        \"name\": \"office\"\n" +
                                                    "                    },\n" +
                                                    "                    \"company\": {\n" +
                                                    "                        \"id\": 2,\n" +
                                                    "                        \"name\": \"nike\"\n" +
                                                    "                    },\n" +
                                                    "                    \"colors\": [\n" +
                                                    "                        \"Red\",\n" +
                                                    "                        \"Blue\",\n" +
                                                    "                        \"Green\"\n" +
                                                    "                    ],\n" +
                                                    "                    \"featured\": true,\n" +
                                                    "                    \"freeShipping\": false,\n" +
                                                    "                    \"stock\": 50,\n" +
                                                    "                    \"averageRating\": 4.5,\n" +
                                                    "                    \"numOfReviews\": 10,\n" +
                                                    "                    \"user\": {\n" +
                                                    "                        \"name\": \"admin\",\n" +
                                                    "                        \"email\": \"admin@gmail.com\",\n" +
                                                    "                        \"role\": \"ADMIN\"\n" +
                                                    "                    },\n" +
                                                    "                    \"reviews\": null\n" +
                                                    "                }\n" +
                                                    "            }\n" +
                                                    "        ]\n" +
                                                    "    }\n" +
                                                    "]"
                                    )

                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "{\n" +
                                                    "    \"message\": \"you are not allowed to get all orders, try to acces as an admin\",\n" +
                                                    "    \"status\": 403\n" +
                                                    "}"
                                    )
                            )
                    )
            }
    )
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orderService.ordersToOrdersDTO(orders), HttpStatus.OK);
    };

    @Operation(
            summary = "Get all orders of the current user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of orders",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "[\n" +
                                                    "    {\n" +
                                                    "        \"user\": {\n" +
                                                    "            \"name\": \"admin\",\n" +
                                                    "            \"email\": \"admin@gmail.com\",\n" +
                                                    "            \"role\": \"ADMIN\"\n" +
                                                    "        },\n" +
                                                    "        \"tax\": 10.0,\n" +
                                                    "        \"shippingFee\": 5.0,\n" +
                                                    "        \"status\": \"PENDING\",\n" +
                                                    "        \"orderItems\": [\n" +
                                                    "            {\n" +
                                                    "                \"amount\": 2,\n" +
                                                    "                \"productId\": 1,\n" +
                                                    "                \"productDTO\": {\n" +
                                                    "                    \"id\": 1,\n" +
                                                    "                    \"name\": \"Sample Product\",\n" +
                                                    "                    \"price\": 29.99,\n" +
                                                    "                    \"description\": \"This is a sample product description.\",\n" +
                                                    "                    \"image\": null,\n" +
                                                    "                    \"category\": {\n" +
                                                    "                        \"id\": 1,\n" +
                                                    "                        \"name\": \"office\"\n" +
                                                    "                    },\n" +
                                                    "                    \"company\": {\n" +
                                                    "                        \"id\": 2,\n" +
                                                    "                        \"name\": \"nike\"\n" +
                                                    "                    },\n" +
                                                    "                    \"colors\": [\n" +
                                                    "                        \"Red\",\n" +
                                                    "                        \"Blue\",\n" +
                                                    "                        \"Green\"\n" +
                                                    "                    ],\n" +
                                                    "                    \"featured\": true,\n" +
                                                    "                    \"freeShipping\": false,\n" +
                                                    "                    \"stock\": 50,\n" +
                                                    "                    \"averageRating\": 4.5,\n" +
                                                    "                    \"numOfReviews\": 10,\n" +
                                                    "                    \"user\": {\n" +
                                                    "                        \"name\": \"admin\",\n" +
                                                    "                        \"email\": \"admin@gmail.com\",\n" +
                                                    "                        \"role\": \"ADMIN\"\n" +
                                                    "                    },\n" +
                                                    "                    \"reviews\": null\n" +
                                                    "                }\n" +
                                                    "            },\n" +
                                                    "            {\n" +
                                                    "                \"amount\": 2,\n" +
                                                    "                \"productId\": 2,\n" +
                                                    "                \"productDTO\": {\n" +
                                                    "                    \"id\": 2,\n" +
                                                    "                    \"name\": \"Sample Product\",\n" +
                                                    "                    \"price\": 29.99,\n" +
                                                    "                    \"description\": \"This is a sample product description.\",\n" +
                                                    "                    \"image\": null,\n" +
                                                    "                    \"category\": {\n" +
                                                    "                        \"id\": 1,\n" +
                                                    "                        \"name\": \"office\"\n" +
                                                    "                    },\n" +
                                                    "                    \"company\": {\n" +
                                                    "                        \"id\": 2,\n" +
                                                    "                        \"name\": \"nike\"\n" +
                                                    "                    },\n" +
                                                    "                    \"colors\": [\n" +
                                                    "                        \"Red\",\n" +
                                                    "                        \"Blue\",\n" +
                                                    "                        \"Green\"\n" +
                                                    "                    ],\n" +
                                                    "                    \"featured\": true,\n" +
                                                    "                    \"freeShipping\": false,\n" +
                                                    "                    \"stock\": 50,\n" +
                                                    "                    \"averageRating\": 4.5,\n" +
                                                    "                    \"numOfReviews\": 10,\n" +
                                                    "                    \"user\": {\n" +
                                                    "                        \"name\": \"admin\",\n" +
                                                    "                        \"email\": \"admin@gmail.com\",\n" +
                                                    "                        \"role\": \"ADMIN\"\n" +
                                                    "                    },\n" +
                                                    "                    \"reviews\": null\n" +
                                                    "                }\n" +
                                                    "            }\n" +
                                                    "        ]\n" +
                                                    "    },\n" +
                                                    "    {\n" +
                                                    "        \"user\": {\n" +
                                                    "            \"name\": \"admin\",\n" +
                                                    "            \"email\": \"admin@gmail.com\",\n" +
                                                    "            \"role\": \"ADMIN\"\n" +
                                                    "        },\n" +
                                                    "        \"tax\": 10.0,\n" +
                                                    "        \"shippingFee\": 5.0,\n" +
                                                    "        \"status\": \"PENDING\",\n" +
                                                    "        \"orderItems\": [\n" +
                                                    "            {\n" +
                                                    "                \"amount\": 2,\n" +
                                                    "                \"productId\": 1,\n" +
                                                    "                \"productDTO\": {\n" +
                                                    "                    \"id\": 1,\n" +
                                                    "                    \"name\": \"Sample Product\",\n" +
                                                    "                    \"price\": 29.99,\n" +
                                                    "                    \"description\": \"This is a sample product description.\",\n" +
                                                    "                    \"image\": null,\n" +
                                                    "                    \"category\": {\n" +
                                                    "                        \"id\": 1,\n" +
                                                    "                        \"name\": \"office\"\n" +
                                                    "                    },\n" +
                                                    "                    \"company\": {\n" +
                                                    "                        \"id\": 2,\n" +
                                                    "                        \"name\": \"nike\"\n" +
                                                    "                    },\n" +
                                                    "                    \"colors\": [\n" +
                                                    "                        \"Red\",\n" +
                                                    "                        \"Blue\",\n" +
                                                    "                        \"Green\"\n" +
                                                    "                    ],\n" +
                                                    "                    \"featured\": true,\n" +
                                                    "                    \"freeShipping\": false,\n" +
                                                    "                    \"stock\": 50,\n" +
                                                    "                    \"averageRating\": 4.5,\n" +
                                                    "                    \"numOfReviews\": 10,\n" +
                                                    "                    \"user\": {\n" +
                                                    "                        \"name\": \"admin\",\n" +
                                                    "                        \"email\": \"admin@gmail.com\",\n" +
                                                    "                        \"role\": \"ADMIN\"\n" +
                                                    "                    },\n" +
                                                    "                    \"reviews\": null\n" +
                                                    "                }\n" +
                                                    "            },\n" +
                                                    "            {\n" +
                                                    "                \"amount\": 2,\n" +
                                                    "                \"productId\": 2,\n" +
                                                    "                \"productDTO\": {\n" +
                                                    "                    \"id\": 2,\n" +
                                                    "                    \"name\": \"Sample Product\",\n" +
                                                    "                    \"price\": 29.99,\n" +
                                                    "                    \"description\": \"This is a sample product description.\",\n" +
                                                    "                    \"image\": null,\n" +
                                                    "                    \"category\": {\n" +
                                                    "                        \"id\": 1,\n" +
                                                    "                        \"name\": \"office\"\n" +
                                                    "                    },\n" +
                                                    "                    \"company\": {\n" +
                                                    "                        \"id\": 2,\n" +
                                                    "                        \"name\": \"nike\"\n" +
                                                    "                    },\n" +
                                                    "                    \"colors\": [\n" +
                                                    "                        \"Red\",\n" +
                                                    "                        \"Blue\",\n" +
                                                    "                        \"Green\"\n" +
                                                    "                    ],\n" +
                                                    "                    \"featured\": true,\n" +
                                                    "                    \"freeShipping\": false,\n" +
                                                    "                    \"stock\": 50,\n" +
                                                    "                    \"averageRating\": 4.5,\n" +
                                                    "                    \"numOfReviews\": 10,\n" +
                                                    "                    \"user\": {\n" +
                                                    "                        \"name\": \"admin\",\n" +
                                                    "                        \"email\": \"admin@gmail.com\",\n" +
                                                    "                        \"role\": \"ADMIN\"\n" +
                                                    "                    },\n" +
                                                    "                    \"reviews\": null\n" +
                                                    "                }\n" +
                                                    "            }\n" +
                                                    "        ]\n" +
                                                    "    }\n" +
                                                    "]"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Forbidden",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "{\n" +
                                                    "    \"message\": \"No orders found for the current user\",\n" +
                                                    "    \"status\": 404\n" +
                                                    "}"
                                    )
                            )
                    ),

            }
    )
    @GetMapping("/showAllMyOrders")
    public ResponseEntity<List<OrderDTO>> getCurrentUserOrders() {
        List<Order> orders = orderService.getCurrentUserOrders();
        return new ResponseEntity<>(orderService.ordersToOrdersDTO(orders), HttpStatus.OK);
    }

    @Operation(
            summary = "Get a single order",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Order found",
                            content = @Content(
                                    mediaType = "application/json",

                                    schema = @Schema(
                                            example = "{\n" +
                                                    "    \"user\": {\n" +
                                                    "        \"name\": \"admin\",\n" +
                                                    "        \"email\": \"admin@gmail.com\",\n" +
                                                    "        \"role\": \"ADMIN\"\n" +
                                                    "    },\n" +
                                                    "    \"tax\": 10.0,\n" +
                                                    "    \"shippingFee\": 5.0,\n" +
                                                    "    \"status\": \"PENDING\",\n" +
                                                    "    \"orderItems\": [\n" +
                                                    "        {\n" +
                                                    "            \"amount\": 2,\n" +
                                                    "            \"productId\": 1,\n" +
                                                    "            \"productDTO\": {\n" +
                                                    "                \"id\": 1,\n" +
                                                    "                \"name\": \"Sample Product\",\n" +
                                                    "                \"price\": 29.99,\n" +
                                                    "                \"description\": \"This is a sample product description.\",\n" +
                                                    "                \"image\": null,\n" +
                                                    "                \"category\": {\n" +
                                                    "                    \"id\": 1,\n" +
                                                    "                    \"name\": \"office\"\n" +
                                                    "                },\n" +
                                                    "                \"company\": {\n" +
                                                    "                    \"id\": 2,\n" +
                                                    "                    \"name\": \"nike\"\n" +
                                                    "                },\n" +
                                                    "                \"colors\": [\n" +
                                                    "                    \"Red\",\n" +
                                                    "                    \"Blue\",\n" +
                                                    "                    \"Green\"\n" +
                                                    "                ],\n" +
                                                    "                \"featured\": true,\n" +
                                                    "                \"freeShipping\": false,\n" +
                                                    "                \"stock\": 50,\n" +
                                                    "                \"averageRating\": 4.5,\n" +
                                                    "                \"numOfReviews\": 10,\n" +
                                                    "                \"user\": {\n" +
                                                    "                    \"name\": \"admin\",\n" +
                                                    "                    \"email\": \"admin@gmail.com\",\n" +
                                                    "                    \"role\": \"ADMIN\"\n" +
                                                    "                },\n" +
                                                    "                \"reviews\": null\n" +
                                                    "            }\n" +
                                                    "        },\n" +
                                                    "        {\n" +
                                                    "            \"amount\": 2,\n" +
                                                    "            \"productId\": 2,\n" +
                                                    "            \"productDTO\": {\n" +
                                                    "                \"id\": 2,\n" +
                                                    "                \"name\": \"Sample Product\",\n" +
                                                    "                \"price\": 29.99,\n" +
                                                    "                \"description\": \"This is a sample product description.\",\n" +
                                                    "                \"image\": null,\n" +
                                                    "                \"category\": {\n" +
                                                    "                    \"id\": 1,\n" +
                                                    "                    \"name\": \"office\"\n" +
                                                    "                },\n" +
                                                    "                \"company\": {\n" +
                                                    "                    \"id\": 2,\n" +
                                                    "                    \"name\": \"nike\"\n" +
                                                    "                },\n" +
                                                    "                \"colors\": [\n" +
                                                    "                    \"Red\",\n" +
                                                    "                    \"Blue\",\n" +
                                                    "                    \"Green\"\n" +
                                                    "                ],\n" +
                                                    "                \"featured\": true,\n" +
                                                    "                \"freeShipping\": false,\n" +
                                                    "                \"stock\": 50,\n" +
                                                    "                \"averageRating\": 4.5,\n" +
                                                    "                \"numOfReviews\": 10,\n" +
                                                    "                \"user\": {\n" +
                                                    "                    \"name\": \"admin\",\n" +
                                                    "                    \"email\": \"admin@gmail.com\",\n" +
                                                    "                    \"role\": \"ADMIN\"\n" +
                                                    "                },\n" +
                                                    "                \"reviews\": null\n" +
                                                    "            }\n" +
                                                    "        }\n" +
                                                    "    ]\n" +
                                                    "}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Order not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "{\n" +
                                                    "    \"message\": \"Order not found\",\n" +
                                                    "    \"status\": 404\n" +
                                                    "}"
                                    )
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getSingleOrder(@PathVariable Long id) {
        Order order = orderService.getSingleOrder(id);
        return new ResponseEntity<>(orderService.toOrderDTO(order), HttpStatus.OK);
    }


    @Operation(
            summary = "Update an order to be paid or not",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Order object that needs to be updated",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\n" +
                                            "    \"paymentIntentId\": \"payment id example\"\n" +
                                            "}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Order updated",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = OrderDTO.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Order not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "{\n" +
                                                    "    \"message\": \"Order not found\",\n" +
                                                    "    \"status\": 404\n" +
                                                    "}"
                                    )
                            )
                    )
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        Order updatedOrder = orderService.updateOrder(id, order);
        return new ResponseEntity<>(orderService.toOrderDTO(updatedOrder), HttpStatus.OK);
    }
}