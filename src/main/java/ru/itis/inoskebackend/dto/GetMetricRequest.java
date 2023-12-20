package ru.itis.inoskebackend.dto;

import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

public record GetMetricRequest(
        @RequestParam(required = false)
        List<String> shopNames,
        @RequestParam(required = false)
        LocalDate dateStart,
        @RequestParam(required = false)
        LocalDate dateEnd
) { }
