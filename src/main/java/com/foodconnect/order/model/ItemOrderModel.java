package com.foodconnect.order.model;

import com.foodconnect.order.model.key.ItemOrderKey;
import jakarta.persistence.*;

@Entity
@Table(name = "item_order")
public class ItemOrderModel {

    @EmbeddedId
    private ItemOrderKey id;

    private int quantity;

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
}
