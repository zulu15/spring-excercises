INSERT INTO productos (create_at, nombre, precio) VALUES ('2023-03-03','Camara Sony', 90000);
INSERT INTO productos (create_at, nombre, precio) VALUES ('2023-03-03','Celular Motorola E22', 75000);
INSERT INTO productos (create_at, nombre, precio) VALUES ('2023-03-03','Televisor LG 25 pulgadas', 120000);
INSERT INTO productos (create_at, nombre, precio) VALUES ('2023-03-03','Parlantes Bluetooh Panasonic', 58000);
INSERT INTO productos (create_at, nombre, precio) VALUES ('2023-03-03','Home theather', 35000);
INSERT INTO productos (create_at, nombre, precio) VALUES ('2023-03-03','Notebook HP 2023', 130000);

INSERT INTO clientes (nombre, apellido, email, created_at, foto) VALUES('Joaquin','Sanchez','jis199422@gmail.com','2023-02-22', '');
INSERT INTO clientes (nombre, apellido, email, created_at, foto) VALUES('Matias','Perez','mperez@gmail.com','2023-02-22', '');

INSERT INTO facturas (descripcion, observacion, fecha, cliente_id) VALUES ('Factura de venta', null, NOW(), 1);
INSERT INTO facturas (descripcion, observacion, fecha, cliente_id) VALUES ('Compra Compumundo', null, NOW(), 1);
INSERT INTO facturas_items (factura_id, cantidad, producto_id) VALUES (1,1,1);
INSERT INTO facturas_items (factura_id, cantidad, producto_id) VALUES (1,1,6);
INSERT INTO facturas_items (factura_id, cantidad, producto_id) VALUES (1,2,4);
INSERT INTO facturas_items (factura_id, cantidad, producto_id) VALUES (2,1,3);
INSERT INTO facturas_items (factura_id, cantidad, producto_id) VALUES (2,2,5);

/* Creamos algunos usuarios con sus roles */
INSERT INTO users (username, password, enabled) VALUES ('joaquin','$2a$10$8NBWIC60GnGiY1RuyvoKqu0NdhrJlqWWOqNJIFKoJfFlWncZvKZC2',1);
INSERT INTO users (username, password, enabled) VALUES ('juan','$2a$10$yI5q5fbIunDRwfjYEhVhpu5PEGHJCbteWNwKx0mJCVE9TWAuSc8Nm',1);

INSERT INTO authorities (user_id, authority) VALUES (1,'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (1,'ROLE_ADMIN');
INSERT INTO authorities (user_id, authority) VALUES (2,'ROLE_USER');