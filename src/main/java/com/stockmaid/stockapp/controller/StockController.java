package com.stockmaid.stockapp.controller;

import com.stockmaid.stockapp.model.Stock;
import com.stockmaid.stockapp.service.StockService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/all")
    public List<Stock> getAll() {
        return stockService.getAll();
    }

    @GetMapping("/sku/{sku}")
    public Stock getById(@PathVariable("sku") long sku) throws Exception {
        return stockService.getBySku(sku);
    }

    @GetMapping("/manufacturer/{manufacturer}")
    public List<Stock> getByManufacturer(@PathVariable("manufacturer") String manufacturer) {
        return stockService.getByManufacturer(manufacturer);
    }

    @GetMapping("/date/{from}/{to}")
    public List<Stock> getDate(@PathVariable("from") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
                               @PathVariable("to") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to) {
        return stockService.getDate(from, to);
    }

}
