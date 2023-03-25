package com.kushanthsliit.shopmanagement.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SummaryResponse {

    private double oysterSold;
    private double tobaccoSold;
    private double tobaccoSoldQuantity;
    private double phoneCardsSold;
    private double zTotal;
    private double shopSales;
    private double shop;
    private double phoneCardPurchased;
    private double tobaccoPurchased;
    private double tobaccoPurchasedQuantity;
    private double wages;
    private double expenses;
    private double commision;
}
