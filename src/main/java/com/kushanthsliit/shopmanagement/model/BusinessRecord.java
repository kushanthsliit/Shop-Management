package com.kushanthsliit.shopmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private LocalDate date;
    private double oysterSold;
    private double tobaccoSold;
    private double tobaccoSoldQuantity;
    private double phoneCardsSold;
    private double total;
    private double shopSales;

    private double shop;
    private double phoneCardsPurchased;
    private double tobaccoPurchased;
    private double tobaccoPurchasedQuantity;
    private double wages;
    private double expenses;
    private double commision;

}
