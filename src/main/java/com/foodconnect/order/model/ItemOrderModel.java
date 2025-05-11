package com.foodconnect.order.model;

import com.foodconnect.order.dto.request.CartInfoRequestDTO;
import com.foodconnect.order.model.key.ItemOrderKey;
import jakarta.persistence.*;

@Entity
@Table(name = "item_order")
public class ItemOrderModel {

    @EmbeddedId
    private ItemOrderKey id;
    private int quantity;
    private double unitPrice;

    public ItemOrderModel() {
    }

    public ItemOrderModel(CartInfoRequestDTO dto) {
        this.quantity = dto.getQuantity();
        this.unitPrice = dto.getUnitPrice();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ItemOrderKey getId() {
        return id;
    }

    public void setId(ItemOrderKey id) {
        this.id = id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
