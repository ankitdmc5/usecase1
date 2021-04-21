package com.ankitjain.stock.service.repository;

import com.ankitjain.stock.service.entity.Stock;
import com.ankitjain.stock.service.valueobjects.StockVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    @Query("SELECT s FROM Stock s where s.companyCode=?1 and s.date between ?2 and ?3 order by s.stockPrice DESC")
    public List<Stock> findStockByDates(Long CompanyCd, Date startdate, Date enddate);

    public List<Stock> findFirstByCompanyCodeOrderByStockPriceDesc(Long companyCode);

    @Query("delete FROM Stock s where s.companyCode=?1")
    void deleteAllByCompanyCode(Long companyCode);
}
