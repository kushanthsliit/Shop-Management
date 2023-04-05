package com.kushanthsliit.shopmanagement.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfitsDto {

    private double tobaccoProfit;
    private double tobaccoQtyProfit;
    private double phoneCardsProfit;
    private double shopProfit;
    private double totalExpenses;
    private double totalProfit;

}
