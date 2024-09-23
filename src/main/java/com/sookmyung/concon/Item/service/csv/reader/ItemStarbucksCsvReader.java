package com.sookmyung.concon.Item.service.csv.reader;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.sookmyung.concon.Item.Entity.Item;
import com.sookmyung.concon.Item.repository.ItemRepository;
import com.sookmyung.concon.Item.service.csv.itemMapper.StarbucksItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemStarbucksCsvReader{
    private final ItemRepository itemRepository;
    private final StarbucksItemMapper starbucksItemMapper;

    @Transactional
    public void importItemFromCsv(String fileName) throws IOException, CsvException {
        List<Item> items = parseCsvFile(fileName);
        System.out.println(items.size());
        itemRepository.saveAll(items);
    }

    public List<Item> parseCsvFile(String fileName) throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new InputStreamReader(
                new ClassPathResource(fileName).getInputStream()))) {
            return reader.readAll().stream()
                    .skip(1)
                    .map(starbucksItemMapper::mapToUser)
                    .toList();
        }
    }
}
