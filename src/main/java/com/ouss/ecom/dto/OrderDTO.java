package com.ouss.ecom.dto;

import com.ouss.ecom.entities.Order;
import com.ouss.ecom.entities.OrderItem;
import com.ouss.ecom.entities.Order.Status;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderDTO {
    private UserDTO user;
    private Double tax;
    private Double shippingFee;
    private Status status;
    private List<OrderItemDTO> orderItems;

    public static OrderDTO fromOrder(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setTax(order.getTax());
        dto.setShippingFee(order.getShippingFee());
        dto.setOrderItems(order.getOrderItems().stream().map(OrderItemDTO::fromOrderItem).collect(Collectors.toList()));
        dto.setUser(UserDTO.toUserDTO(order.getUser()));
        dto.setStatus(order.getStatus());
        return dto;
    }

}