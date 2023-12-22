package ru.itis.inoskebackend.mapper;

import ru.itis.inoskebackend.model.Metric;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
public class MetricMapper {

    public List<Metric> mapToMetrics(List<Map<String, Object>> maps) {
        return maps.stream().map(this::mapToMetric).toList();
    }

    private Metric mapToMetric(Map<String, Object> map) {
        String shopName = (String) map.get("shop_name");
        Long productAdded = (Long) map.get("product_added");
        Long categoryAdded = (Long) map.get("category_added");
        Long visiting = (Long) map.get("visiting");
        Long purchases = (Long) map.get("purchases");
        Long registrations = (Long) map.get("registrations");
        LocalDate date = (LocalDate) map.get("date");

        return new Metric(shopName, productAdded, categoryAdded, visiting, purchases, registrations, date);
    }
}
