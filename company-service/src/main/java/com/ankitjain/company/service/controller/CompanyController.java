package com.ankitjain.company.service.controller;

import com.ankitjain.company.service.entity.Company;
import com.ankitjain.company.service.service.CompanyService;
import com.ankitjain.company.service.valueobjects.CompanyVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1.0/market")
@Slf4j
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping("/register")
    public Company registerCompany(@Valid @RequestBody Company requestDto) {
        log.info("Inside registerCompany method");
        return this.companyService.register(requestDto);
    }

    @GetMapping("/info/{companyCode}")
    public CompanyVO getCompanyDetail(@PathVariable Long companyCode) throws Exception {
        return this.companyService.getCompanyDetail(companyCode);
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
