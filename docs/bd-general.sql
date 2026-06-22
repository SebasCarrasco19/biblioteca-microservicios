-- Base de datos general del proyecto Biblioteca Microservicios
-- MySQL 8+
-- Cada microservicio conserva una base de datos independiente.
-- Hibernate está configurado con ddl-auto=update; este archivo permite crear
-- previamente las bases y documenta la estructura principal.

CREATE DATABASE IF NOT EXISTS db_users CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_auth CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_security CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_categories CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_books CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_copies CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_loans CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_reservations CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_fines CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_notifications CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE db_users;
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefono VARCHAR(255) NOT NULL UNIQUE,
    estado VARCHAR(255) NOT NULL,
    fecha_creacion DATETIME(6) NOT NULL
);

USE db_auth;
CREATE TABLE IF NOT EXISTS credenciales (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    estado VARCHAR(255) NOT NULL,
    fecha_creacion DATETIME(6) NOT NULL
);

USE db_security;
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL UNIQUE,
    descripcion VARCHAR(255) NOT NULL,
    estado VARCHAR(255) NOT NULL,
    fecha_creacion DATETIME(6) NOT NULL
);
CREATE TABLE IF NOT EXISTS usuarios_roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    estado VARCHAR(255) NOT NULL,
    fecha_asignacion DATETIME(6) NOT NULL
);

USE db_categories;
CREATE TABLE IF NOT EXISTS categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(200) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    fecha_creacion DATETIME(6) NOT NULL
);

USE db_books;
CREATE TABLE IF NOT EXISTS books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    isbn VARCHAR(20) NOT NULL UNIQUE,
    description VARCHAR(500),
    author VARCHAR(120) NOT NULL,
    editorial VARCHAR(120) NOT NULL,
    category_id BIGINT NOT NULL,
    published_year INT NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL
);

USE db_copies;
CREATE TABLE IF NOT EXISTS copies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    book_id BIGINT NOT NULL,
    inventory_code VARCHAR(60) NOT NULL UNIQUE,
    location VARCHAR(120) NOT NULL,
    status VARCHAR(30) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    fecha_registro DATETIME(6) NOT NULL
);

USE db_loans;
CREATE TABLE IF NOT EXISTS loans (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    copy_id BIGINT NOT NULL,
    loan_date DATE NOT NULL,
    due_date DATE NOT NULL,
    return_date DATE,
    status VARCHAR(20) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL
);

USE db_reservations;
CREATE TABLE IF NOT EXISTS reservations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    copy_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL,
    active BOOLEAN NOT NULL,
    reservation_date DATETIME(6) NOT NULL,
    expiration_date DATETIME(6) NOT NULL
);

USE db_fines;
CREATE TABLE IF NOT EXISTS fines (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    loan_id BIGINT NOT NULL,
    days_late INT NOT NULL,
    amount DOUBLE NOT NULL,
    reason VARCHAR(200) NOT NULL,
    paid BOOLEAN NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    paid_at DATETIME(6),
    cancelled_at DATETIME(6),
    updated_at DATETIME(6) NOT NULL
);

USE db_notifications;
CREATE TABLE IF NOT EXISTS notifications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    title VARCHAR(255),
    message VARCHAR(255),
    type VARCHAR(255),
    status VARCHAR(255),
    created_at DATETIME(6),
    sent_at DATETIME(6),
    read_at DATETIME(6),
    updated_at DATETIME(6)
);
