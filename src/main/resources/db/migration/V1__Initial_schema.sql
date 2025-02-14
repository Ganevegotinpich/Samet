-- Създаване на таблица за клиенти
CREATE TABLE customers (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           email VARCHAR(255) NOT NULL,
                           has_accidents BOOLEAN NOT NULL
);

-- Създаване на таблица за автомобили
CREATE TABLE cars (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      brand VARCHAR(255) NOT NULL,
                      model VARCHAR(255) NOT NULL,
                      location VARCHAR(255) NOT NULL,
                      price_per_day DOUBLE NOT NULL,  -- Променено на price_per_day
                      available BOOLEAN NOT NULL
);

-- Създаване на таблица за оферти
CREATE TABLE offers (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        car_id BIGINT NOT NULL,
                        customer_id BIGINT NOT NULL,
                        rental_days INT NOT NULL,
                        total_price DOUBLE NOT NULL,
                        accepted BOOLEAN NOT NULL,
                        FOREIGN KEY (car_id) REFERENCES cars(id),
                        FOREIGN KEY (customer_id) REFERENCES customers(id)
);
