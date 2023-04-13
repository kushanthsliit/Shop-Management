package com.kushanthsliit.shopmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartData {

    private String chartType;
    private String[] chartLabels;
    private List<Integer> chartValues;
}
