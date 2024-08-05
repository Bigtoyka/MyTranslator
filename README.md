# MyTranslator

## Руководство
1. Настроить подключение к Google Translator API:
   в файле `application.properties` в rapidapi.key вставить свой ключ

2. Настройте подключение к базе данных PostgreSQL:
    - Создать таблицу:
        ```sql
        CREATE TABLE translation_requests (
            id BIGSERIAL PRIMARY KEY,
            ip_address VARCHAR(255) NOT NULL,
            input_string TEXT NOT NULL,
            translated_string TEXT NOT NULL,
            request_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        );
        ```
    - В файле `application.properties` ввести свой порт в localhost и название DB, логин и пароль:
        ```properties
        spring.datasource.url=jdbc:postgresql://localhost:5432/YOUR_DB
        spring.datasource.username=YOUR_USERNAME
        spring.datasource.password=YOUR_PASSWORD
        ```
3. Запустить проект.

4. Приложение будет доступно по адресу `http://localhost:8080`.

5. Открыть https://web.postman.co/workspace/My-Workspace~029659d4-a12e-433f-8cc2-608a1a575aa1/overview. 
- Нажать New -> HTTP, выбрать необходимый запрос, ввести http://localhost:8080/translate
- В поле ввести запрос, после чего нажать Send

## Пример запросов
- "q" - Предложение, которое нужно перевести
- "source" - Исходный язык
- "target" - Целевой язык

Для выполнения запроса на перевод, отправьте POST-запрос
```sh
{
  "q": "Hello world, this is my first program",
  "source": "en",
  "target": "ru",
  "format": "text"
}
```
Чтобы получить список доступных языков, отправьте Get-запрос http://localhost:8080/languages

