CREATE TABLE IF NOT EXISTS propietario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    primer_apellido VARCHAR(255) NOT NULL,
    segundo_apellido VARCHAR(255),
    email VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS mascota (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50),
    fecha_nac DATE,
    raza VARCHAR(50),
    peso INT,
    tiene_chip BOOLEAN,
    url_foto VARCHAR(255),
    propietario_id BIGINT NULL,
    FOREIGN KEY (propietario_id) REFERENCES propietario(id)
);
