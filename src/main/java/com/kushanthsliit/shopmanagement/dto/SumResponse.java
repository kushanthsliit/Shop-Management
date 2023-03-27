package com.kushanthsliit.shopmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SumResponse {

    private double sumOfWages;
    private double sumOfTobaccoSold;
}
