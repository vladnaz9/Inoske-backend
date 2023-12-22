package ru.itis.inoskebackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Metric {
    private String shopName;
    private Long productsAdded;
    private Long categoryUpdated;
    private Long visiting;
    private Long purchases;
    private Long registrations;
    private LocalDate date;
}
