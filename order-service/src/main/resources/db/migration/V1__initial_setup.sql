CREATE TABLE orders
(
    id           BIGSERIAL PRIMARY KEY ,
    order_number VARCHAR(255) DEFAULT NULL UNIQUE,
    sku_code     VARCHAR(255) NOT NULL,
    price        DECIMAL(19, 2) NOT NULL CHECK (price >= 0),
    quantity     INTEGER NOT NULL CHECK (quantity > 0)
);
