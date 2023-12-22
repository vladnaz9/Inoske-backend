package ru.itis.inoskebackend.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ru.itis.inoskebackend.dto.GetMetricRequest;
import ru.itis.inoskebackend.mapper.MetricMapper;
import ru.itis.inoskebackend.model.Metric;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class MetricRepository {

    private static final String GET_METRIC_QUERY = """
            SELECT *
            FROM metric            
              """;

    private static final String SHOP_NAME_FILTER = """
            shop_name in (#{shopNames})
            """;

    private static final String DATE_FILTER = """
            date between '#{dateStart}' AND '#{dateEnd}'
            """;

    private static final String UPLOAD_METRIC_QUERY = """
            INSERT INTO default.metric (shop_name, product_added, category_added, visiting, purchases, registrations, date) VALUES 
            """;

    private static final String METRIC_VALUE = """
            ('#{shopName}', #{productAdded}, #{categoryAdded}, #{visiting}, #{purchases}, #{registrations}, '#{date}'),
            """;

    private final JdbcTemplate template;
    private final MetricMapper mapper;

    MetricRepository(JdbcTemplate template, MetricMapper mapper) {
        this.template = template;
        this.mapper = mapper;
    }

    public List<Metric> getMetric(GetMetricRequest metricRequest) {
        boolean whereFlag = false;
        String query = GET_METRIC_QUERY;
        if (!metricRequest.shopNames().trim().isEmpty()) {
            List<String> shopNamesList = Arrays.stream(metricRequest.shopNames().trim().split(",")).toList();
            if (!CollectionUtils.isEmpty(shopNamesList)) {
                String shopNames = String.join(",", shopNamesList.stream().map(shopName -> "'" + shopName + "'").toList());
                query = query + " WHERE ";
                whereFlag = true;
                query = query + SHOP_NAME_FILTER.replace("#{shopNames}", shopNames);
            }
        }

        LocalDate dateStart = metricRequest.dateStart() != null ? metricRequest.dateStart() : LocalDate.now();
        LocalDate dateEnd = metricRequest.dateEnd() != null ? metricRequest.dateEnd() : LocalDate.now();
        if (!whereFlag) {
            query = query + " WHERE ";
            whereFlag = true;
        } else {
            query = query + " AND ";
        }
        query = query + DATE_FILTER.replace("#{dateStart}", dateStart.toString()).replace("#{dateEnd}", dateEnd.toString());

        return mapper.mapToMetrics(template.queryForList(query));
    }

    public void upload(List<Metric> metrics) {
        String query = UPLOAD_METRIC_QUERY;
        for (Metric metric : metrics) {
            query = query + METRIC_VALUE
                    .replace("#{shopName}", metric.getShopName())
                    .replace("#{productAdded}", metric.getProductsAdded().toString())
                    .replace("#{categoryAdded}", metric.getCategoryUpdated().toString())
                    .replace("#{visiting}", metric.getVisiting().toString())
                    .replace("#{purchases}", metric.getPurchases().toString())
                    .replace("#{registrations}", metric.getRegistrations().toString())
                    .replace("#{date}", metric.getDate().toString());
        }
        template.update(query);
    }
}
