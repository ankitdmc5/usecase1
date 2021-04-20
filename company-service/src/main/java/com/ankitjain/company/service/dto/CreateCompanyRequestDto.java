package com.ankitjain.company.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class CreateCompanyRequestDto {
    @NotEmpty
    private String CompanyName;
    @NotEmpty
    private String CompanyCEO;
    private Double turnOver;
    @NotEmpty
    private String website;
    @NotEmpty
    private List<String> listedAt = new ArrayList<>();
}
