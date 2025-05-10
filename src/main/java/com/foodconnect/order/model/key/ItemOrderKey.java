package com.foodconnect.order.model.key;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foodconnect.order.model.OrderModel;
import com.foodconnect.order.model.ProductModel;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class ItemOrderKey {

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "fk_order_id")
    private OrderModel orderId;

    @ManyToOne
    @JoinColumn(name = "fk_product_id")
    private ProductModel productId;

    public OrderModel getOrderId() {
        return orderId;
    }

    public void setOrderId(OrderModel orderId) {
        this.orderId = orderId;
    }

    public ProductModel getProductId() {
        return productId;
    }

    public void setProductId(ProductModel productId) {
        this.productId = productId;
    }
}
