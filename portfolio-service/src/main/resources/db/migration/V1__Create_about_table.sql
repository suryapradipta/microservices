CREATE TABLE abouts
(
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(255),
    description TEXT,
    imgUrl      VARCHAR(255),
    createdBy   VARCHAR(255),
    createdAt   TIMESTAMP,
    updatedBy   VARCHAR(255),
    updatedAt   TIMESTAMP
);