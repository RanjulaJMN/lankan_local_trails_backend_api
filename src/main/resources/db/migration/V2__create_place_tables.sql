CREATE TABLE place (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    description TEXT NULL,
    latitude DECIMAL(10,8) NOT NULL,
    longitude DECIMAL(11,8) NOT NULL,
    opening_time VARCHAR(50) NOT NULL,
    closing_time VARCHAR(50) NOT NULL,
    distance FLOAT NOT NULL,
    travel_tips TEXT,
    image_url VARCHAR(255) NULL,
    created_by BIGINT,
    deleted_by BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL,
    deleted_at TIMESTAMP NULL
);

CREATE TABLE place_category (
    pc_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_id BIGINT,
    place_id BIGINT,
    FOREIGN KEY (category_id) REFERENCES category(id),
    FOREIGN KEY (place_id) REFERENCES place(id)
);