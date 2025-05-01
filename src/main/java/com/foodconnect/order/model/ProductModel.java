package com.foodconnect.order.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;


@Entity
@Table(name = "product")
@Immutable
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "store_id")
    private Long storeId;


    public Long getId() {
        return id;
    }

    public Long getStoreId() {
        return storeId;
    }
}
