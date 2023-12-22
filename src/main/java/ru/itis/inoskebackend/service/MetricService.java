package ru.itis.inoskebackend.service;

import org.springframework.stereotype.Service;
import ru.itis.inoskebackend.dto.GetMetricRequest;
import ru.itis.inoskebackend.dto.UploadFakeMetricsRequest;
import ru.itis.inoskebackend.model.Metric;
import ru.itis.inoskebackend.repository.MetricRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class MetricService {

    private final MetricRepository metricRepository;

    private final List<String> shopNames = Arrays.asList("wildberries", "ozon", "goldenApple", "yandexMarket", "amazon", "lamoda", "ostin", "zarina", "sella", "sohaShop", "respect", "noOne", "uniqlo", "karry", "sexShop");

    private final Map<String, Set<LocalDate>> fakerDataAssigned = new HashMap<>();

    public MetricService(MetricRepository metricRepository) {
        this.metricRepository = metricRepository;
    }

    public List<Metric> getMetric(GetMetricRequest metricRequest) {
        return metricRepository.getMetric(metricRequest);
    }

    public void upload(List<Metric> metrics) {
        metricRepository.upload(metrics);
    }

    public void uploadFakeMetrics(UploadFakeMetricsRequest uploadFakeMetricsRequest) {
        List<Metric> metrics = generateFakeMetrics(uploadFakeMetricsRequest.count());
        upload(metrics);
    }

    private List<Metric> generateFakeMetrics(Long count) {
        ArrayList<Metric> metrics = new ArrayList<>();
        int i = 0;
        while (i != count) {
            String shopName = shopNames.get((int) random(0, shopNames.size()));
            Long productsAdded = random(1_000, 100_000);
            Long categoryUpdated = random(100, 10_000);
            Long visiting = random(100_000, 1_000_000);
            Long purchases = random(50_000, 500_000);
            Long registrations = random(10_000, 300_000);
            LocalDate date = random(LocalDate.now().minusMonths(1), LocalDate.now());
            if (fakerDataAssigned.get(shopName) == null || !fakerDataAssigned.get(shopName).contains(date)) {
                metrics.add(new Metric(shopName, productsAdded, categoryUpdated, visiting, purchases, registrations, date));
                i++;
                if (!fakerDataAssigned.containsKey(shopName)) {
                    HashSet<LocalDate> dataSet = new HashSet<>();
                    dataSet.add(date);
                    fakerDataAssigned.put(shopName, dataSet);
                } else {
                    fakerDataAssigned.get(shopName).add(date);
                }
            }
        }

        return metrics;
    }

    private long random(long min, long max) {
        return ThreadLocalRandom.current().nextLong(min, max);
    }

    private LocalDate random(LocalDate start, LocalDate end) {
        long startEpochDay = start.toEpochDay();
        long endEpochDay = end.toEpochDay();
        long randomDay = ThreadLocalRandom
                .current()
                .nextLong(startEpochDay, endEpochDay);

        return LocalDate.ofEpochDay(randomDay);
    }
}
