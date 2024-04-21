package com.ouss.ecom.services;

import com.ouss.ecom.dao.OrderItemRepo;
import com.ouss.ecom.dao.ProductRepo;
import com.ouss.ecom.dao.UserRepo;
import com.ouss.ecom.dto.OrderDTO;
import com.ouss.ecom.dto.OrderItemDTO;
import com.ouss.ecom.dto.ProductDTO;
import com.ouss.ecom.entities.*;
import com.ouss.ecom.dao.OrderRepository;
import com.ouss.ecom.errors.CustomException;
import com.ouss.ecom.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;
    private final OrderItemRepo orderItemRepo;

    public OrderService(OrderRepository orderRepository, ProductRepo productRepo, UserRepo userRepo, OrderItemRepo orderItemRepo) {
        this.orderRepository = orderRepository;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
        this.orderItemRepo = orderItemRepo;
    }

    public Order createOrder(OrderDTO orderDTO) {
            List<OrderItemDTO> cartItemsDTO = orderDTO.getOrderItems();
            Double tax = orderDTO.getTax();
            Double shippingFee = orderDTO.getShippingFee();
            AppUser user = SecurityUtil.getAuthenticatedUser();
            if (cartItemsDTO == null || cartItemsDTO.isEmpty()) {
                throw new CustomException.BadRequestException("No cart items provided");
            }
            if (tax == null || shippingFee == null) {
                throw new CustomException.BadRequestException("Please provide tax and shipping fee");
            }

            Double subtotal = 0.0;
            Double total = 0.0;

            Order order = new Order();
            order.setSubtotal(subtotal);
            order.setTax(tax);
            order.setShippingFee(shippingFee);
            order.setUser(user);
            order.setTotal(total);

            Order savedOrder = orderRepository.save(order);
            for (OrderItemDTO item : cartItemsDTO) {
                Product dbProduct = productRepo.findById(item.getProductId())
                        .orElseThrow(() -> new CustomException.NotFoundException("No product with id : " + item.getProductId()));
                OrderItem existingOrderItem = orderItemRepo.findByOrderAndProduct(savedOrder, dbProduct);
                if (existingOrderItem != null) {
                    throw new CustomException.BadRequestException("Duplicate value entered here, please choose another value");
                }
                OrderItem orderItem = OrderItem.builder()
                        .name(dbProduct.getName())
                        .image(dbProduct.getImage())
                        .price(dbProduct.getPrice())
                        .amount(item.getAmount())
                        .product(dbProduct)
                        .build();
                orderItem.setId(new OrderItemKey(savedOrder.getId(), dbProduct.getId()));

                orderItem.setOrder(savedOrder);

                orderItem = orderItemRepo.save(orderItem);
                savedOrder.getOrderItems().add(orderItem);
                subtotal += item.getAmount() * dbProduct.getPrice();
            }
            // Assuming fakeStripeAPI is another service method
//            String clientSecret = fakeStripeAPI(total, "usd");
            String clientSecret = "fake_client_secret_stripe";
            savedOrder.setClientSecret(clientSecret);
            total = tax + shippingFee + subtotal;
            savedOrder.setTotal(total);
            savedOrder.setSubtotal(subtotal);

            return orderRepository.save(savedOrder);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getCurrentUserOrders() {
        List<Order> orders = orderRepository.findByUserId(SecurityUtil.getAuthenticatedUser().getId()).orElseThrow(
                () -> new CustomException.NotFoundException("No orders found for the current user")
        );
        return orders;
    }

    public Order getSingleOrder(Long id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new CustomException.NotFoundException("No order with id : " + id)
        );
    }

    public Order updateOrder(Long id, Order order) {
        Order existingOrder = orderRepository.findById(id).orElseThrow(
                () -> new CustomException.NotFoundException("No order with id : " + id)
        );
        existingOrder.setPaymentIntentId(order.getPaymentIntentId());
        existingOrder.setStatus(Order.Status.PAID);
        return orderRepository.save(existingOrder);
    }
    public List<OrderDTO> ordersToOrdersDTO(List<Order> orders) {
        return orders.stream().map(
                this::toOrderDTO
        ).collect(Collectors.toList());
    }
    public OrderDTO toOrderDTO(Order order) {
        OrderDTO orderDTO = OrderDTO.fromOrder(order);
        orderDTO.getOrderItems().forEach(orderItemDTO -> {
            ProductDTO productDTO = ProductDTO.toProductDTO(
                    productRepo.findById(orderItemDTO.getProductId()).orElseThrow(
                            () -> new CustomException.NotFoundException("Product not found")
                    )
            );
            orderItemDTO.setProductDTO(productDTO);
        });
        return orderDTO;
    }
}