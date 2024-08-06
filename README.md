# MyTranslator

## Руководство
1. Настроить подключение к Google Translator API:
   В директории resources в файле `application.properties` в rapidapi.key вставить свой ключ

2. Настроить подключение к базе данных PostgreSQL:
    - Создать таблицу в PostgreSQL:
        ```sql
        CREATE TABLE translation_requests (
            id BIGSERIAL PRIMARY KEY,
            ip_address VARCHAR(255) NOT NULL,
            input_string TEXT NOT NULL,
            translated_string TEXT NOT NULL,
            request_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        );
        ```
    - В директории resources в файле `application.properties` в localhost ввести вместо "5432" свой порт и название DB вместо "YOUR_DB", 
   логин и пароль от базы данных вместо YOUR_USERNAME и YOUR_PASSWORD соответственно:
        ```properties
        spring.datasource.url=jdbc:postgresql://localhost:5432/YOUR_DB
        spring.datasource.username=YOUR_USERNAME
        spring.datasource.password=YOUR_PASSWORD
        ```
3. Запустить проект.

4. Приложение будет доступно по адресу `http://localhost:8080`.

5. В графе "исходный язык" ввести в формате "ru" обозначение языка, с которого будет осуществляться перевод
В графе "целевой язык" ввести в формате "en" обозначение языка, на который будет осуществляться перевод
В графе "текст для перевода" ввести предложение

Чтобы получить список языков и их обозначения, нужно перейти по адресу http://localhost:8080/languages

