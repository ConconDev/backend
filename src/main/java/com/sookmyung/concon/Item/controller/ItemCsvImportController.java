package com.sookmyung.concon.Item.controller;

import com.sookmyung.concon.Item.service.ItemCsvReader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ItemCsvImportController {
    private final ItemCsvReader itemCsvReader;

    @PostMapping("/api/import-csv")
    public String importCsv() {
        try {
            itemCsvReader.importItemFromCsv("starbucks_drink.csv");
            return "CSV 파일 가져오기 성공";
        } catch (Exception e) {
            return "CSV 파일 가져오기 실패" + e.getMessage();
        }
    }
}
