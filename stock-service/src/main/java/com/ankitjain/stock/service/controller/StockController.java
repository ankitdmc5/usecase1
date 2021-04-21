package com.ankitjain.stock.service.controller;

import com.ankitjain.stock.service.entity.Stock;
import com.ankitjain.stock.service.service.StockService;
import com.ankitjain.stock.service.valueobjects.StockVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1.0/stock")
@Slf4j
public class StockController {

    @Autowired
    private StockService service;

    @PostMapping("/add/{companyCode}")
    public ResponseEntity<Stock> addStockPrice(@PathVariable Long companyCode, @Valid @RequestBody Stock stock) throws Exception {
        log.info("Inside addStockPrice method");
        stock.setCompanyCode(companyCode);
        stock.setDate(new Date());
        Stock saved = this.service.addStockPrice(stock);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @GetMapping("/get/{companyCode}/{startdate}/{enddate}")
    public StockVO getStockPriceList(@PathVariable Long companyCode,
                                     @PathVariable String startdate,
                                     @PathVariable String enddate) throws Exception {
        log.info("Inside getStockPriceList method");
        Date date1=new SimpleDateFormat("dd-MM-yyyy").parse(startdate);
        Date date2=new SimpleDateFormat("dd-MM-yyyy").parse(enddate);
        return this.service.getStockPriceList(companyCode, date1, date2);
    }

    @GetMapping("/getLatest/{companyCode}")
    public Stock getLatestStockPrice(@PathVariable Long companyCode) throws Exception {
        log.info("Inside getLatestStockPrice method");
        return this.service.getLatestStockPrice(companyCode);
    }

    @DeleteMapping("/deleteAll/{companyCode}")
    public ResponseEntity<String> deleteAllByCompanyCode(@PathVariable Long companyCode) throws Exception {
        this.service.deleteAllByCompanyCode(companyCode);
        return new ResponseEntity<String>("Deleted Successfully", HttpStatus.ACCEPTED);
    }

    @GetMapping("/test")
    public String test(){
        return "Test stock service";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String handleExceptions(
            Exception ex) {
        return ex.getMessage();
    }

}
