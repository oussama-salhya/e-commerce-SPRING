package com.ouss.ecom.dto;

import com.ouss.ecom.dao.OrderItemRepo;
import com.ouss.ecom.entities.OrderItem;
import com.ouss.ecom.entities.OrderItemKey;
import lombok.Data;

@Data
public class OrderItemDTO {

    private Integer amount;
    private Long productId;
    private ProductDTO productDTO;

    public static OrderItemDTO fromOrderItem(OrderItem orderItem) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setAmount(orderItem.getAmount());
        dto.setProductId(orderItem.getProduct().getId());
        return dto;
    }
}
