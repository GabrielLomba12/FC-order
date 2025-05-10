package com.foodconnect.order.dto.response;

import java.util.List;

public class OrderDetailsDTO {

    private List<ProductDTO> products;
    private OrderHistoryDTO orderHistory;

    public OrderDetailsDTO(){}

    public OrderDetailsDTO(List<ProductDTO> products, OrderHistoryDTO orderHistory){
        this.products = products;
        this.orderHistory = orderHistory;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public OrderHistoryDTO getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(OrderHistoryDTO orderHistory) {
        this.orderHistory = orderHistory;
    }
}
