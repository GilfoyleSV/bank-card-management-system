# Bank Card Management System

# Bank Card Management System (CMS)

![Java](https://img.shields.io/badge/Java-17%2B-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-blue)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue)
![Docker](https://img.shields.io/badge/Docker-Compose-blue)

RESTful API сервис для управления банковскими картами, выполнения внутренних переводов и разграничения доступа на основе ролевой модели (RBAC).

---

## 🚀 Технологический стек

* **Core**: Java 17+, Spring Boot
* **Security**: Spring Security, JWT (JSON Web Token)
* **Persistence**: Spring Data JPA, Hibernate
* **Database**: PostgreSQL / MySQL
* **Database Migrations**: Liquibase
* **Documentation**: OpenAPI 3.0 / Swagger UI
* **DevOps & Containerization**: Docker, Docker Compose
* **Testing**: JUnit 5, Mockito

---

## 🛠 Функциональные возможности

### 🔒 Безопасность и Доступ
* **Аутентификация & Авторизация**: Авторизация по JWT-токену.
* **Ролевая модель**:
  * `ADMIN`: Полный доступ к картам и пользователям (создание, блокировка, активация, удаление, просмотр всех карт).
  * `USER`: Доступ только к личным картам (просмотр, блокировка, переводы между своими картами).
* **Защита данных**:
  * Маскирование номеров карт в API (формат: `**** **** **** 1234`).
  * Шифрование чувствительных данных.

### 💳 Бизнес-логика
* **Управление картами**: CRUD-операции с поддержкой статусов (`ACTIVE`, `BLOCKED`, `EXPIRED`).
* **Переводы**: Безопасный перевод средств между картами одного владельца.
* **Поиск и Навигация**: Пагинация, фильтрация и сортировка списков карт.

---

## 📋 Атрибуты сущности "Карта"

| Поле | Тип данных | Описание |
| :--- | :--- | :--- |
| `id` | `Long` / `UUID` | Уникальный идентификатор |
| `cardNumber` | `String` | Зашифрованный номер (в API выдается маска `**** **** **** 1234`) |
| `owner` | `User` | Владелец карты |
| `expirationDate` | `LocalDate` | Срок действия карты |
| `status` | `Enum` | Статус (`ACTIVE`, `BLOCKED`, `EXPIRED`) |
| `balance` | `BigDecimal` | Текущий баланс карты |

---

## 📄 Документация API

Интерактивная документация Swagger UI доступна после запуска приложения:

* **Swagger UI**: `http://localhost:8080/swagger-ui.html`
* **OpenAPI Spec**: `docs/openapi.yaml` (или `http://localhost:8080/v3/api-docs`)

### Основные эндпоинты

#### 🔑 Аутентификация (`/api/v1/auth`)
* `POST /login` — Получение JWT токена.
* `POST /register` — Регистрация нового пользователя.

#### 💳 Карты (`/api/v1/cards`)
* `GET /` — Получение списка карт (для `USER` — только свои, для `ADMIN` — все; поддерживает пагинацию и фильтрацию).
* `GET /{id}` — Просмотр подробной информации по карте.
* `POST /` — Создание новой карты (`ADMIN`).
* `PATCH /{id}/status` — Изменение статуса карты (`ADMIN` — активация/блокировка, `USER` — запрос на блокировку).
* `DELETE /{id}` — Удаление карты (`ADMIN`).

#### 🔄 Переводы (`/api/v1/transfers`)
* `POST /` — Перевод средств между своими картами (`USER`).

---

## 🚦 Быстрый запуск

### Предварительные требования
* Installed [Docker](https://www.docker.com/) & [Docker Compose](https://docs.docker.com/compose/)
* Installed [JDK 17+](https://adoptium.net/) (для локальной сборки)