package com.ankitjain.stock.service.valueobjects;

import com.ankitjain.stock.service.entity.Stock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@ToString
public class StockVO {
    private List<Stock> stocks = new ArrayList<>();
    private Double maxStockPrice;
    private Double minStockPrice;
    private Double avgStockPrice;
}
