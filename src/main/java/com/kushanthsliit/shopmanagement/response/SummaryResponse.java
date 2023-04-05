package com.kushanthsliit.shopmanagement.response;

import com.kushanthsliit.shopmanagement.dto.GetAllSums;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SummaryResponse {

    private GetAllSums getAllSums;
    private ProfitsDto profits;

}
