# inoske-backend
Сервис ответсвенный за работу с агрегированными метриками онлайн магазинов при помощи ClickHouse
## Используемые технологии
* Java v.17
* spring-boot v.3.2.0
* ClickHouse v.23
  * JDBC driver v.0.3.2-patch5
* Maven v.4

## Запуск проекта с использованием Maven
    mvn spring-boot:run

    Приложение будет доступно на порту 8080

## Clickhouse

### Структура таблиц

Таблица событий
- shop_name - название магазина  
- product_added - количество добавленных продуктов
- category_added - количество обновленных регистраций
- visiting - количество посещений
- purchases - количество покупок
- registrations - количество регистраций
- date - дата метрики

###  Запуск clickhouse  

выполнить комманду в корне проекта.
`docker compose up -d`

### Создание таблицы

`CREATE TABLE metric
(
    shop_name String,
    product_added UInt256,
    category_added UInt256,
    visiting UInt256,
    purchases UInt256,
    registrations UInt256,
    date DATE
) ENGINE = MergeTree ORDER BY date;`
