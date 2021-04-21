package com.ankitjain.company.service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyCode;
    @NotBlank(message = "CompanyName is mandatory")
    private String companyName;
    @NotBlank(message = "companyCEO is mandatory")
    private String companyCEO;
    @Min(10000001)
    private Double turnOver;
    @NotBlank(message = "website is mandatory")
    private String website;
    @ElementCollection(fetch = FetchType.EAGER)
    @NotNull
    private List<String> listedAt = new ArrayList<>();
}
