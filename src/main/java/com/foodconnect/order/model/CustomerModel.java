package com.foodconnect.order.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "customer")
@Immutable
public class CustomerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "phoneNumber", unique = true)
    private String phoneNumber;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "customer_credential_id")
    private CustomerCredentialModel credential;

    public CustomerModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public CustomerCredentialModel getCredential() {
        return credential;
    }

    public void setCredential(CustomerCredentialModel credential) {
        this.credential = credential;
    }
}
