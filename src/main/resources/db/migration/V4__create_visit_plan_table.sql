CREATE TABLE visit_plan (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    plan_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE visit_plan_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    plan_id BIGINT NOT NULL,
    place_id BIGINT NOT NULL,
    `order` INT NOT NULL,
    arrival_time TIME NOT NULL,
    departure_time TIME NOT NULL,
    travel_time_minutes INT,
    distance_km FLOAT,
    FOREIGN KEY (plan_id) REFERENCES visit_plan(id) ON DELETE CASCADE,
    FOREIGN KEY (place_id) REFERENCES place(id)
);