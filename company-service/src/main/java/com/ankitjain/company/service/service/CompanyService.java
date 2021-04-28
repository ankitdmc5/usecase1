package com.ankitjain.company.service.service;

import com.ankitjain.company.service.entity.Company;
import com.ankitjain.company.service.entity.Stock;
import com.ankitjain.company.service.repository.CompanyRepository;
import com.ankitjain.company.service.valueobjects.CompanyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private RestTemplate restTemplate;

    public Company register(Company company) {
        return this.companyRepository.save(company);
    }

    public CompanyVO getCompanyDetail(Long companyCode) throws Exception {
        Optional<Company> company = this.companyRepository.findById(companyCode);
        if (company.isPresent()) {
            ResponseEntity<Stock> response = this.restTemplate.exchange("http://STOCK-SERVICE/api/v1.0/market/stock/getLatest/" + companyCode, HttpMethod.GET, null, Stock.class);
            return CompanyVO.builder().company(company.get()).latestStockPrice(response.getBody().getStockPrice()).build();
        } else {
            throw new Exception("Company code not found");
        }
    }

    public List<CompanyVO> getAllCompanies() {
        List<CompanyVO> resList = new ArrayList<>();
        List<Company> companies = this.companyRepository.findAll();
        companies.forEach(company -> {
            ResponseEntity<Stock> response = this.restTemplate.exchange("http://STOCK-SERVICE/api/v1.0/market/stock/getLatest/" + company.getCompanyCode(), HttpMethod.GET, null, Stock.class);
            resList.add(CompanyVO.builder()
                    .company(company)
                    .latestStockPrice(response.getBody().getStockPrice())
                    .build());
        });
        return resList;
    }

    public void deleteCompanyWithAllStockPrices(Long companyCode) throws Exception {
        Optional<Company> company = this.companyRepository.findById(companyCode);
        if (company.isPresent()) {
            this.companyRepository.deleteById(companyCode);
            this.restTemplate.exchange("http://STOCK-SERVICE/api/v1.0/market/stock/deleteAll/" + companyCode, HttpMethod.DELETE, null, String.class);
        } else {
            throw new Exception("Company does not exist");
        }
    }
}
