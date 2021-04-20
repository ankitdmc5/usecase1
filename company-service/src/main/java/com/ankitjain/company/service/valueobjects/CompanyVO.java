package com.ankitjain.company.service.valueobjects;

import com.ankitjain.company.service.entity.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CompanyVO {
    private Company company;
    private Double latestStockPrice;
}
