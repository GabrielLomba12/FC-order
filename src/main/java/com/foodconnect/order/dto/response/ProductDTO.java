package com.foodconnect.order.dto.response;

import com.foodconnect.order.model.ProductModel;

public class ProductDTO {

    private String productName;
    private Double price;
    private int quantity;

    public ProductDTO() {
    }

    public ProductDTO(ProductModel model, int quantity) {
        this.productName = model.getName();
        this.price = model.getPrice();
        this.quantity = quantity;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
