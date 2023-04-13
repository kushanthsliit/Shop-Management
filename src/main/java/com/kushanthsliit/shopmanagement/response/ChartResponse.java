package com.kushanthsliit.shopmanagement.response;

import com.kushanthsliit.shopmanagement.dto.ChartData;
import lombok.Data;

@Data
public class ChartResponse {

    private ChartData lineChartData;
    private ChartData barChartData;
    private ChartData doughnutChartData;
    private ChartData pieChartData;
    private SummaryResponse summaryResponse;
}
