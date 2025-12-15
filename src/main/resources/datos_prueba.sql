-- Script para crear la tabla y meter datos de prueba
-- Ejecutar esto en pgAdmin o en la consola de PostgreSQL

-- Primero creamos la base de datos si no existe (ejecutar esto aparte)
-- CREATE DATABASE "Enemigos";

-- Conectarse a la base de datos Enemigos y ejecutar lo siguiente:

-- Crear la tabla (aunque hibernate la crea sola con ddl-auto=update)
CREATE TABLE IF NOT EXISTS ENEMIGOS_DEL_ESTADO (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255),
    pais VARCHAR(255),
    afiliacion VARCHAR(255)
);

-- Borrar datos anteriores si hay (por si quieres empezar de cero)
-- DELETE FROM ENEMIGOS_DEL_ESTADO;

-- Insertar datos de prueba
INSERT INTO ENEMIGOS_DEL_ESTADO (nombre, pais, afiliacion) VALUES
('Gandalf el Gris', 'La Comarca', 'Istari'),
('Aragorn', 'Gondor', 'Dunedain'),
('Legolas', 'Bosque Negro', 'Elfos Silvanos'),
('Gimli', 'Montañas Azules', 'Enanos de Erebor'),
('Frodo Bolson', 'La Comarca', 'Hobbits'),
('Samwise Gamyi', 'La Comarca', 'Hobbits'),
('Theoden', 'Rohan', 'Rohirrim'),
('Eomer', 'Rohan', 'Rohirrim'),
('Faramir', 'Gondor', 'Montaraces de Ithilien'),
('Elrond', 'Rivendel', 'Elfos Noldor');

-- Para ver que se insertaron bien
SELECT * FROM ENEMIGOS_DEL_ESTADO;
