package com.sookmyung.concon.Item.controller;

import com.sookmyung.concon.Item.service.csv.reader.ItemCgvCsvReader;
import com.sookmyung.concon.Item.service.csv.reader.ItemStarbucksCsvReader;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "CSV 파일 매핑")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/import-csv")
public class ItemCsvImportController {
    private final ItemStarbucksCsvReader itemStarbucksCsvReader;
    private final ItemCgvCsvReader itemCgvCsvReader;

    @Operation(summary = "스타벅스 데이터")
    @PostMapping("/starbucks")
    public String importStarbucksCsv() {
        try {
            itemStarbucksCsvReader.importItemFromCsv("starbucks_drink.csv");
            return "CSV 파일 가져오기 성공";
        } catch (Exception e) {
            return "CSV 파일 가져오기 실패: " + e.getMessage();
        }
    }

    @Operation(summary = "cgv 데이터")
    @PostMapping("/cgv")
    public String importCgvCsv() {
        try {
            itemCgvCsvReader.importItemFromCsv("cgv_food_info.csv");
            return "CSV 파일 가져오기 성공";
        } catch (Exception e) {
            return "CSV 파일 가져오기 실패: " + e.getMessage();
        }
    }
}
