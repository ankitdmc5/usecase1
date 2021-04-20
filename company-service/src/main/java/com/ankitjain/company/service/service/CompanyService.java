package com.ankitjain.company.service.service;

import com.ankitjain.company.service.entity.Company;
import com.ankitjain.company.service.repository.CompanyRepository;
import com.ankitjain.company.service.valueobjects.CompanyVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    ObjectMapper objectMapper;

    public Company register(Company company) {
        return this.companyRepository.save(company);
    }

    public CompanyVO getCompanyDetail(Long companycode) throws Exception {
        Optional<Company> company = this.companyRepository.findById(companycode);
        if (company.isPresent()) {
            return CompanyVO.builder().company(company.get()).latestStockPrice(0.0D).build();
        } else {
            throw new Exception("Company code not found");
        }
    }
}
