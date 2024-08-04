-- Insertar propietarios
INSERT INTO propietario (nombre, email) VALUES
('Juan Pérez', 'juan.perez@example.com'),
('Ana García', 'ana.garcia@example.com'),
('Luis Fernández', 'luis.fernandez@example.com'),
('María López', 'maria.lopez@example.com');

-- Insertar mascotas (algunas con propietario_id NULL porque no es necesario)
INSERT INTO mascota (nombre, fecha_nac, raza, peso, tiene_chip, url_foto, propietario_id) VALUES
('Bobby', '2022-01-01', 'Labrador', 20, true, '/images/mascota1.jpg', NULL),
('Lucy', '2021-05-15', 'Bulldog', 18, false, '/images/mascota2.jpg', 1),
('Max', '2020-11-20', 'Beagle', 12, true, '/images/mascota3.jpg', NULL),  
('Bella', '2022-02-10', 'Poodle', 8, true, '/images/mascota4.jpg', 2),
('Charlie', '2019-06-25', 'Golden Retriever', 25, false, '/images/mascota5.jpg', NULL),  
('Molly', '2021-09-05', 'Shih Tzu', 7, true, '/images/mascota6.jpg', 3),
('Cooper', '2020-12-12', 'German Shepherd', 30, false, '/images/mascota7.jpg', 4),
('Daisy', '2022-03-22', 'Chihuahua', 5, false, '/images/mascota8.jpg', NULL),  
('Rocky', '2021-01-14', 'Boxer', 23, false, '/images/mascota9.jpg', NULL),
('Luna', '2020-08-30', 'Pug', 10, true, '/images/mascota10.jpg', 2),
('Jake', '2022-04-25', 'Dalmatian', 22, true, '/images/mascota11.jpg', NULL),
('Zoe', '2021-07-20', 'Rottweiler', 35, false, '/images/mascota12.jpg', 4),
('Jack', '2020-05-10', 'Doberman', 28, true, '/images/mascota13.jpg', 1),
('Lily', '2019-11-11', 'Cocker Spaniel', 12, false, '/images/mascota14.jpg', NULL),
('Toby', '2021-02-14', 'Great Dane', 40, false, '/images/mascota15.jpg', 3),
('Sadie', '2020-03-15', 'Siberian Husky', 25, false, '/images/mascota16.jpg', NULL),
('Bailey', '2021-10-10', 'French Bulldog', 13, true, '/images/mascota17.jpg', 1),
('Maggie', '2019-12-20', 'Yorkshire Terrier', 6, false, '/images/mascota18.jpg', NULL),
('Buddy', '2022-05-05', 'Australian Shepherd', 22, true, '/images/mascota19.jpg', NULL),
('Chloe', '2021-08-01', 'Cavalier King Charles Spaniel', 9, false, '/images/mascota20.jpg', NULL);
