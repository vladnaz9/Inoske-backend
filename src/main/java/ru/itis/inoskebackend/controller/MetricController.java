package ru.itis.inoskebackend.controller;

import ru.itis.inoskebackend.dto.GetMetricRequest;
import ru.itis.inoskebackend.dto.UploadFakeMetricsRequest;
import ru.itis.inoskebackend.model.Metric;
import ru.itis.inoskebackend.service.MetricService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metrics")
class MetricController {

    private final MetricService metricService;

    public MetricController(MetricService metricService) {
        this.metricService = metricService;
    }

    @GetMapping
    public List<Metric> getMetric(GetMetricRequest metricRequest) {
        return metricService.getMetric(metricRequest);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "OK")
    public void upload(@RequestBody List<Metric> metrics) {
        metricService.upload(metrics);
    }

    @PostMapping("/faker")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "OK")
    public void uploadFakeMetrics(@RequestBody UploadFakeMetricsRequest uploadFakeMetricsRequest) {
        metricService.uploadFakeMetrics(uploadFakeMetricsRequest);
    }
}
