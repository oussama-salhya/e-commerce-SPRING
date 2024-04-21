package com.ouss.ecom.controllers;

import com.ouss.ecom.dao.ProductRepo;
import com.ouss.ecom.dto.OrderDTO;
import com.ouss.ecom.dto.ProductDTO;
import com.ouss.ecom.entities.Order;
import com.ouss.ecom.errors.CustomException;
import com.ouss.ecom.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO order) {
        Order createdOrder = orderService.createOrder(order);
        return new ResponseEntity<>(orderService.toOrderDTO(createdOrder), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orderService.ordersToOrdersDTO(orders), HttpStatus.OK);
    };

    @GetMapping("/showAllMyOrders")
    public ResponseEntity<List<OrderDTO>> getCurrentUserOrders() {
        List<Order> orders = orderService.getCurrentUserOrders();
        return new ResponseEntity<>(orderService.ordersToOrdersDTO(orders), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getSingleOrder(@PathVariable Long id) {
        Order order = orderService.getSingleOrder(id);
        return new ResponseEntity<>(orderService.toOrderDTO(order), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        Order updatedOrder = orderService.updateOrder(id, order);
        return new ResponseEntity<>(orderService.toOrderDTO(updatedOrder), HttpStatus.OK);
    }
}