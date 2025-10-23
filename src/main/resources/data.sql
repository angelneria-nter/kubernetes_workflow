-- ##################################################################
-- #                     DATA FOR 'country' TABLE                   #
-- ##################################################################
INSERT INTO country (code, name) VALUES
('ES', 'Spain'),
('US', 'United States'),
('GB', 'United Kingdom'),
('DE', 'Germany'),
('FR', 'France'),
('IT', 'Italy'),
('JP', 'Japan'),
('CA', 'Canada'),
('AU', 'Australia'),
('MX', 'Mexico'),
('BR', 'Brazil'),
('IN', 'India');

-- ##################################################################
-- #                      DATA FOR 'users' TABLE                    #
-- ##################################################################
-- Nota: La contraseña debería estar hasheada. Uso un texto plano como placeholder.
-- Se incluyen usuarios activos e inactivos.
INSERT INTO users (full_name, email, password, created_at, is_active, country_code, rol) VALUES
('Ana García', 'ana.garcia@example.com', '$2a$12$SGF2AzBiVMwkh64MocYXhO1rt9Prcq8EzNFrQiieRxrWLqUacRiPm', '2025-01-15 10:30:00', true, 'ES', 'USER'), --contraseña user1234
('John Smith', 'john.smith@example.com', '$2a$12$SGF2AzBiVMwkh64MocYXhO1rt9Prcq8EzNFrQiieRxrWLqUacRiPm', '2025-02-20 11:00:00', true, 'US', 'USER'),
('Maria Rodriguez', 'maria.rodriguez@example.com', '$2a$12$SGF2AzBiVMwkh64MocYXhO1rt9Prcq8EzNFrQiieRxrWLqUacRiPm', '2025-03-10 12:15:00', true, 'MX', 'USER'),
('David Chen', 'david.chen@example.com', '$2a$12$SGF2AzBiVMwkh64MocYXhO1rt9Prcq8EzNFrQiieRxrWLqUacRiPm', '2025-04-05 14:00:00', true, 'CA', 'USER'),
('Sophie Dubois', 'sophie.dubois@example.com', '$2a$12$SGF2AzBiVMwkh64MocYXhO1rt9Prcq8EzNFrQiieRxrWLqUacRiPm', '2025-05-22 09:45:00', true, 'FR', 'USER'),
('Luca Rossi', 'luca.rossi@example.com', '$2a$12$SGF2AzBiVMwkh64MocYXhO1rt9Prcq8EzNFrQiieRxrWLqUacRiPm', '2025-06-18 18:30:00', false, 'IT', 'USER'), -- Usuario inactivo
('Yuki Tanaka', 'yuki.tanaka@example.com', '$2a$12$SGF2AzBiVMwkh64MocYXhO1rt9Prcq8EzNFrQiieRxrWLqUacRiPm', '2025-07-01 08:00:00', true, 'JP', 'USER'),
('Carlos Silva', 'carlos.silva@example.com', '$2a$12$SGF2AzBiVMwkh64MocYXhO1rt9Prcq8EzNFrQiieRxrWLqUacRiPm', '2025-08-11 20:00:00', true, 'BR', 'USER'),
('Emily White', 'emily.white@example.com', '$2a$12$SGF2AzBiVMwkh64MocYXhO1rt9Prcq8EzNFrQiieRxrWLqUacRiPm', '2025-09-03 13:20:00', true, 'GB', 'USER'),
('Klaus Mueller', 'klaus.mueller@example.com', '$2a$12$SGF2AzBiVMwkh64MocYXhO1rt9Prcq8EzNFrQiieRxrWLqUacRiPm', '2025-09-25 15:55:00', true, 'DE', 'USER'),
('Usuario Inactivo Dos', 'inactive.user@example.com', '$2a$12$SGF2AzBiVMwkh64MocYXhO1rt9Prcq8EzNFrQiieRxrWLqUacRiPm', '2025-01-01 00:00:00', false, 'US', 'USER'), -- Otro usuario inactivo
('Admin', 'admin@example.com', '$2a$12$6E90u3AAQtFOjtkLbrs/2OtPgRdpDY9mlfDKV9ZuLPo.dz18llKMS', '2025-01-01 00:00:00', true, 'ES', 'ADMIN'); --contraseña admin123

-- ##################################################################
-- #                    DATA FOR 'products' TABLE                   #
-- ##################################################################
-- Se incluyen productos con diferentes estados.
INSERT INTO products (name, price, product_status, created_at) VALUES
('Laptop Pro X15', 1499.99, 'AVAILABLE', '2025-01-10'),
('Smartphone Zenith Z', 899.50, 'AVAILABLE', '2025-01-12'),
('Wireless Headphones', 199.99, 'AVAILABLE', '2025-02-01'),
('Smartwatch Plus', 249.00, 'OUT_OF_STOCK', '2025-02-15'),
('4K Monitor 27"', 450.75, 'AVAILABLE', '2025-03-05'),
('Mechanical Keyboard', 120.00, 'AVAILABLE', '2025-03-20'),
('Gaming Mouse', 79.99, 'AVAILABLE', '2025-04-11'),
('Tablet Pro 11"', 650.00, 'DELETED', '2025-04-25'), -- Producto eliminado
('Webcam HD 1080p', 59.95, 'AVAILABLE', '2025-05-02'),
('USB-C Hub', 39.99, 'OUT_OF_STOCK', '2025-05-18'),
('External SSD 1TB', 130.50, 'AVAILABLE', '2025-06-09'),
('Ergonomic Chair', 320.00, 'AVAILABLE', '2025-06-21'),
('Smart Coffee Mug', 89.90, 'AVAILABLE', '2025-07-07'),
('Bluetooth Speaker', 110.00, 'DELETED', '2025-07-19'), -- Producto eliminado
('E-Reader Pro', 140.00, 'AVAILABLE', '2025-08-01'),
('LED Desk Lamp', 45.50, 'AVAILABLE', '2025-08-14'),
('VR Headset', 399.99, 'OUT_OF_STOCK', '2025-08-30'),
('Action Camera', 299.00, 'AVAILABLE', '2025-09-05'),
('Power Bank 20000mAh', 49.99, 'AVAILABLE', '2025-09-15'),
('Digital Drawing Tablet', 99.99, 'AVAILABLE', '2025-09-28');

-- ##################################################################
-- #                     DATA FOR 'orders' TABLE                    #
-- ##################################################################
-- Se incluyen pedidos con diferentes estados y para distintos usuarios.
INSERT INTO orders (status, created_at, user_id) VALUES
('DELIVERED', '2025-08-10 14:00:00', 1), -- Ana García
('SHIPPED', '2025-09-01 10:15:00', 2),   -- John Smith
('PENDING', '2025-09-28 11:30:00', 3),   -- Maria Rodriguez
('PROCESSING', '2025-09-29 09:00:00', 1),-- Ana García
('CANCELLED', '2025-07-20 18:45:00', 4), -- David Chen
('DELIVERED', '2025-06-15 12:00:00', 5), -- Sophie Dubois
('SHIPPED', '2025-09-25 16:20:00', 7),   -- Yuki Tanaka
('DELIVERED', '2025-08-30 13:10:00', 8), -- Carlos Silva
('PENDING', '2025-09-30 08:55:00', 9),   -- Emily White
('PROCESSING', '2025-10-01 10:00:00', 10),-- Klaus Mueller
('DELIVERED', '2025-05-10 11:00:00', 1), -- Ana García (tercer pedido)
('SHIPPED', '2025-09-27 17:00:00', 3),  -- Maria Rodriguez (segundo pedido)
('CANCELLED', '2025-09-15 10:00:00', 2),-- John Smith
('PENDING', '2025-10-02 09:30:00', 8);  -- Carlos Silva

-- ##################################################################
-- #                  DATA FOR 'order_product' TABLE                #
-- ##################################################################
-- Se relaciona cada pedido con varios productos y sus cantidades.
-- Pedido 1 (DELIVERED)
INSERT INTO order_product (order_id, product_id, amount) VALUES
(1, 1, 1), -- Laptop
(1, 7, 1); -- Gaming Mouse

-- Pedido 2 (SHIPPED)
INSERT INTO order_product (order_id, product_id, amount) VALUES
(2, 2, 1), -- Smartphone
(2, 3, 2); -- Headphones (2 unidades)

-- Pedido 3 (PENDING)
INSERT INTO order_product (order_id, product_id, amount) VALUES
(3, 5, 1), -- Monitor
(3, 6, 1), -- Keyboard
(3, 9, 1); -- Webcam

-- Pedido 4 (PROCESSING)
INSERT INTO order_product (order_id, product_id, amount) VALUES
(4, 11, 2), -- SSD (2 unidades)
(4, 19, 3); -- Power Bank (3 unidades)

-- Pedido 5 (CANCELLED)
INSERT INTO order_product (order_id, product_id, amount) VALUES
(5, 13, 1); -- Coffee Mug

-- Pedido 6 (DELIVERED)
INSERT INTO order_product (order_id, product_id, amount) VALUES
(6, 12, 1); -- Ergonomic Chair

-- Pedido 7 (SHIPPED)
INSERT INTO order_product (order_id, product_id, amount) VALUES
(7, 15, 1), -- E-Reader
(7, 16, 2); -- Desk Lamp (2 unidades)

-- Pedido 8 (DELIVERED)
INSERT INTO order_product (order_id, product_id, amount) VALUES
(8, 18, 1); -- Action Camera

-- Pedido 9 (PENDING)
INSERT INTO order_product (order_id, product_id, amount) VALUES
(9, 1, 1), -- Laptop
(9, 5, 1), -- Monitor
(9, 11, 1),-- SSD
(9, 12, 1);-- Chair

-- Pedido 10 (PROCESSING)
INSERT INTO order_product (order_id, product_id, amount) VALUES
(10, 20, 1); -- Drawing Tablet

-- Pedido 11 (DELIVERED)
INSERT INTO order_product (order_id, product_id, amount) VALUES
(11, 3, 1), -- Headphones
(11, 7, 1); -- Mouse

-- Pedido 12 (SHIPPED)
INSERT INTO order_product (order_id, product_id, amount) VALUES
(12, 19, 5); -- Power Bank (5 unidades)

-- Pedido 13 (CANCELLED)
INSERT INTO order_product (order_id, product_id, amount) VALUES
(13, 1, 1); -- Laptop

-- Pedido 14 (PENDING)
INSERT INTO order_product (order_id, product_id, amount) VALUES
(14, 2, 2), -- Smartphone (2 unidades)
(14, 9, 1); -- Webcam