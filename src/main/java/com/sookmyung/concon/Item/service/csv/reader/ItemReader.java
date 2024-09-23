package com.sookmyung.concon.Item.service.csv.reader;

import com.opencsv.exceptions.CsvException;
import com.sookmyung.concon.Item.Entity.Item;

import java.io.IOException;
import java.util.List;

public interface ItemReader {
    void importItemFromCsv(String fileName) throws IOException, CsvException;

    List<Item> parseCsvFile(String fileName) throws IOException, CsvException;
}
