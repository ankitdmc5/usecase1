package com.ankitjain.stock.service.service;

import com.ankitjain.stock.service.entity.Stock;
import com.ankitjain.stock.service.repository.StockRepository;
import com.ankitjain.stock.service.valueobjects.StockVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class StockService {
    @Autowired
    private StockRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public Stock addStockPrice(Stock stock) throws Exception {
        if (this.companyCodeExists(stock.getCompanyCode())) {
            return repository.save(stock);
        } else throw new Exception("Company should be added first to add a stock price");
    }

    private boolean companyCodeExists(Long companyCode) {
        ResponseEntity<String> response = null;
        try {
            response = this.restTemplate.exchange("http://COMPANY-SERVICE/api/v1.0/market/info/" + companyCode, HttpMethod.GET, null, String.class);
        } catch (RestClientException restClientException) {
            return false;
        }
        return true;
    }

    public StockVO getStockPriceList(Long companyCode, Date startdate, Date enddate) throws Exception {
        if (this.companyCodeExists(companyCode)) {
            List<Stock> result = this.repository.findStockByDates(companyCode, startdate, enddate);
            if (result.size() > 0) {
                final StockVO stockVO = StockVO.builder()
                        .stocks(result)
                        .maxStockPrice(result.stream().mapToDouble(Stock::getStockPrice).max().getAsDouble())
                        .minStockPrice(result.stream().mapToDouble(Stock::getStockPrice).min().getAsDouble())
                        .avgStockPrice(result.stream().mapToDouble(Stock::getStockPrice).average().getAsDouble()
                        ).build();
                log.info(stockVO.toString());
                return stockVO;
            } else {
                throw new Exception("Stock price should be added first for this company");
            }
        } else {
            throw new Exception("Company should be added first to add a stock price");
        }
    }

    public Stock getLatestStockPrice(Long companyCode) throws Exception {
        try {
            final Stock stock = this.repository.findFirstByCompanyCodeOrderByStockPriceDesc(companyCode).get(0);
            return stock;
        } catch (Exception e){
            return new Stock();
        }
    }

    public void deleteAllByCompanyCode(Long companyCode) {
        this.repository.deleteAllByCompanyCode(companyCode);
    }
}
