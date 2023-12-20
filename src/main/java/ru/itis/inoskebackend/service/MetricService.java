package ru.itis.inoskebackend.service;

import ru.itis.inoskebackend.dto.GetMetricRequest;
import ru.itis.inoskebackend.dto.UploadFakeMetricsRequest;
import ru.itis.inoskebackend.repository.MetricRepository;
import ru.itis.inoskebackend.model.Metric;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class MetricService {

    private final MetricRepository metricRepository;

    private final List<String> shopNames = Arrays.asList("wildberries", "ozon", "goldenApple", "yandexMarket", "amazon", "lamoda", "ostin", "zarina", "sella", "sohaShop", "respect", "noOne", "uniqlo", "karry", "sexShop");

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
        for (int i = 0; i < count; i++) {
            String shopName = shopNames.get((int) random(0, shopNames.size()));
            Long productsAdded = random(1_000, 100_000);
            Long categoryUpdated = random(100, 10_000);
            Long visiting = random(100_000, 1_000_000);
            Long purchases = random(50_000, 500_000);
            Long registrations = random(10_000, 300_000);
            LocalDate date = random(LocalDate.now().minusMonths(1), LocalDate.now());
            metrics.add(new Metric(shopName, productsAdded, categoryUpdated, visiting, purchases, registrations, date));
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
