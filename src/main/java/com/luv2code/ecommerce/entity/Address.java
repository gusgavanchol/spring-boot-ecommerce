package com.luv2code.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name = "street")
    public String street;

    @Column(name = "city")
    public String city;

    @Column(name = "country")
    public String country;

    @Column(name = "zip_code")
    public String zipCode;

    @OneToOne
    @PrimaryKeyJoinColumn
    public Order order;
}
