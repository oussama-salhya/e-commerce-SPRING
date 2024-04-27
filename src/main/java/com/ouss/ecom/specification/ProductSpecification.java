package com.ouss.ecom.specification;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import com.ouss.ecom.entities.Product;

public class ProductSpecification implements Specification<Product> {

    private String property;
    private String operation;
    private Object value;

    public ProductSpecification(String property, String operation, Object value) {
        this.property = property;
        this.operation = operation;
        this.value = value instanceof String ? ((String) value).toLowerCase() : value;
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (operation.equalsIgnoreCase("eq")) {
            if (property.contains(".")) {
                String[] parts = property.split("\\.");
                Join<Product, ?> join = root.join(parts[0]);
                    return criteriaBuilder.equal(join.get(parts[1]), value);
            } else {
                    return criteriaBuilder.equal(root.get(property), value);
            }
        } else if (operation.equalsIgnoreCase("ge")) {
            return criteriaBuilder.greaterThanOrEqualTo(root.get(property), (Double) value);
        } else if (operation.equalsIgnoreCase("le")) {
            return criteriaBuilder.lessThanOrEqualTo(root.get(property), (Double) value);
        } else if (operation.equalsIgnoreCase("like")) {
                if (property.contains(".")) {
                    String[] parts = property.split("\\.");
                    Join<Product, ?> join = root.join(parts[0]);
                    return criteriaBuilder.like(join.get(parts[1]), "%" + value + "%");
                } else {
                    return criteriaBuilder.like(root.get(property), "%" + value + "%");
                }
        }else if (operation.equalsIgnoreCase("member")) {
            return criteriaBuilder.isMember((String) value, root.get(property));
        }
        return null;
    }
}