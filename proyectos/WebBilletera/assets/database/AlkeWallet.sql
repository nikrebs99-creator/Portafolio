CREATE DATABASE AlkeWallet;  -- Comentada al ya estar creada.
USE AlkeWallet; -- Para habilitar su uso

DROP TABLE IF EXISTS transacciones;
DROP TABLE IF EXISTS contactos;
-- Si no están en cascade, eliminar hijas primero.
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS monedas;
-- Se eliminan para evitar duplicados o problemas


CREATE TABLE usuario  
	(rut_cliente VARCHAR(10) PRIMARY KEY,
    nombre VARCHAR(45) NOT NULL,
    apellidos VARCHAR(60) NOT NULL,
    email VARCHAR(45) NOT NULL UNIQUE,
    direccion VARCHAR(100),
    contrasena VARCHAR(255) NOT NULL,
    saldo DECIMAL(15, 2),
    saldo_moneda VARCHAR(25) DEFAULT 'CLP_ID' -- INNER JOIN para unirlo con TABLE monedas más adelante
    );
 
 SAVEPOINT pre_timestamp_usuario;
 
 ALTER TABLE usuario
 ADD COLUMN fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
 
 -- DDL Modificación estructural para la adición de una columna en usuario
 
 -- ROLLBACK pre_timestamp_usuario
 
CREATE TABLE monedas
	(moneda_id VARCHAR(25) PRIMARY KEY NOT NULL,  -- FOREIGN KEY para transacciones
    nombre_moneda VARCHAR(20) NOT NULL, -- si no lo tiene, no tiene sentido la tabla.
    codigo_iso VARCHAR(20) NOT NULL,  -- USD, CLP, ETC
    simbolo VARCHAR(5) NOT NULL 
    );
    
INSERT INTO monedas () VALUES -- monedas minimas
	-- (moneda_id, nombre_moneda, codigo_iso, simbolo)
    ('USD_ID', 'DOLAR ESTADOUNIDENSE', 'USD', '$'),
    ('CLP_ID', 'PESO CHILENO', 'CLP', '$'),
    ('ARS_ID', 'PESO ARGENTINO', 'ARS', '$'),
    ('CAD_ID', 'DOLAR CANADIENSE', 'CAD', '$'),
    ('AUD_ID', 'DOLAR AUSTRALIANO', 'AUD', '$'),
    ('MXN_ID', 'PESO MEXICANO', 'MXN', '$'),
    ('BRL_ID', 'REAL BRASILENO', 'BRL', 'R$'),
	('COP_ID', 'PESO COLOMBIANO', 'COP', '$'),
	('PEN_ID', 'SOL PERUANO', 'PEN', 'S/.'),
	('VES_ID', 'BOLIVAR SOBERANO', 'VES', 'Bs.D'),
	('UYU_ID', 'PESO URUGUAYO', 'UYU', '$U'),
	('EUR_ID', 'EURO', 'EUR', '€'),
	('GBP_ID', 'LIBRA ESTERLINA', 'GBP', '£'),
	('JPY_ID', 'YEN JAPONES', 'JPY', '¥'),
	('CNY_ID', 'YUAN CHINO', 'CNY', '¥'),
    ('RUB_ID', 'RUBLO RUSO', 'RUB', '₽'),
	('CVE_ID', 'ESCUDO CABOVERDIANO', 'CVE', '$');
    
SELECT * FROM monedas;
    	
CREATE TABLE contactos
	(id_contacto INT AUTO_INCREMENT PRIMARY KEY, -- Usar para mejorar el indexado
    origen_rut VARCHAR(10), -- FOREIGN KEY para usuarios; rut del contacto origen
    nombre_destinatario VARCHAR(100),
    alias VARCHAR(45),
    banco_destino VARCHAR(45),
    numero_cuenta VARCHAR(45),
    contacto_rut VARCHAR(10),
    FOREIGN KEY (origen_rut) REFERENCES usuario(rut_cliente)
    );

CREATE TABLE transacciones  -- Después de monedas por el FOREIGN KEY
	(transaccion_id INT AUTO_INCREMENT PRIMARY KEY, -- para indexado
    sender_id VARCHAR(10) NOT NULL,  -- FOREIGN KEY, rut cliente origen.
    receiver_id VARCHAR(10) NOT NULL,
    moneda_id VARCHAR(25) NOT NULL,
    monto DECIMAL(15,2) NOT NULL,
    
    tipo_transaccion VARCHAR(30) NOT NULL,  -- 0 descuenta, 1 suma. por ahora.
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP, -- por defecto la fecha actual
    FOREIGN KEY (sender_id) REFERENCES usuario(rut_cliente),
    FOREIGN KEY (moneda_id) REFERENCES monedas(moneda_id)
	-- FOREIGN KEY (receiver_id) REFERENCES usuario(rut_cliente) -- No agregado para funcionar para transferencias sin un rut en usuario creado
    );

-- CREADAS LAS TABLAS, VAMOS A PONER A PRUEBA LOS CONCEPTOS -- 
-- Rellenadas las tablas, todas en el mismmo día y hora genera un problema a la hora de agruparlas
-- Problema que se puede resolver con DML, UPDATE table

SAVEPOINT INICIO0; -- Antes de updates DML en tablas

UPDATE transacciones SET fecha = DATE_SUB(fecha, INTERVAL FLOOR(RAND() * 4) DAY);
-- de esta forma a cada transaccion en fecha, se le resta un numero random
-- -------------
-- asimismo, tenemos que tener cuidado de que los correos ingresados no tengan un espacio involuntario
UPDATE usuario SET email = REPLACE(email, ' ', '');

-- ROLLBACK TO INICIO0;


SAVEPOINT avance1;  -- Porsiacaso hubiera algún problema, volvemos al inicio (autocommit off)

-- Si quisieramos seleccionar todos los usuarios para asignarle su correcto simbolo, tendriamos
-- que juntar las tablas de esta manera:

SELECT * FROM usuario
INNER JOIN monedas
ON monedas.moneda_id = usuario.saldo_moneda;

-- Ahora que tenemos conciencia de poder unir esta tablas sin problemas, vamos a unir
-- transacciones y usuario para que se vea la completa información de cada tabla.

-- Esta consulta nos devuelve la información básica de quienes y cuanto transfirió o fue abonado
-- Ordenado por los RUT de manera ASC
SELECT transacciones.transaccion_id, usuario.rut_cliente, usuario.email, transacciones.receiver_id, transacciones.monto, transacciones.fecha, transacciones.tipo_transaccion
FROM transacciones
INNER JOIN usuario ON transacciones.sender_id = usuario.rut_cliente
ORDER BY usuario.rut_cliente ASC;

-- Sin embargo, si quisieramos seleccionar solo un tipo de moneda en particular, tendremos
-- que filtrar con WHERE. transacciones.moneda_id = 'MONEDA_ID'

SELECT * from usuario
INNER JOIN monedas
ON monedas.moneda_id = usuario.saldo_moneda
WHERE usuario.saldo_moneda = 'CLP_ID';

-- ROLLBACK TO avance1;

SAVEPOINT avance2;

/* Si quisieramos consultar la primera transaccion de cada usuario, tenemos un problema ->
si hay usuarios nuevos sin transacciones aún, puesto que podríamos no verlos
y esto puede ser importante para un futuro análisis. Aqui es donde aplica LEFT JOIN
*/

SELECT usuario.*, transacciones.* FROM usuario
LEFT JOIN transacciones 
-- Cruzamos tablas, quedan todos en el reporte y todas las columnas de cada tabla
ON transacciones.sender_id = usuario.rut_cliente 
AND transacciones.tipo_transaccion = 0 
AND transacciones.fecha = (
	-- sub consulta para saber la primera (MIN FECHA)
    SELECT MIN(t2.fecha)
    FROM transacciones t2 WHERE t2.sender_id = usuario.rut_cliente
    AND t2.tipo_transaccion = 1)
ORDER BY usuario.rut_cliente ASC;

-- SELECT * FROM transacciones WHERE sender_id = '11679863-8'
-- asimismo, para seleccionar al top 5 con saldo se puede hacer con

SELECT rut_cliente, nombre, apellidos, email, saldo FROM usuario
ORDER BY saldo DESC LIMIT 5;


-- ROLLBACK TO avance2
SAVEPOINT avance3;

-- Esto para saber la cantidad de transacciones
SELECT usuario.rut_cliente, usuario.nombre,
(SELECT COUNT(*) FROM transacciones WHERE transacciones.sender_id = usuario.rut_cliente) AS total_envios
FROM usuario;

-- Sin embargo, si queremos saber si está haciendo buen uso de su dinero, es más complejo
SELECT usuario.rut_cliente, usuario.nombre, usuario.email,
(SELECT SUM(CASE
				WHEN transacciones.tipo_transaccion = '1' THEN transacciones.monto
				WHEN transacciones.tipo_transaccion = '0' THEN -transacciones.monto
                ELSE 0
                -- De esta forma, suma los ingresos, resta los descuentos. Si no tiene, no hace nada
			END) FROM transacciones WHERE transacciones.sender_id = usuario.rut_cliente) AS balance_neto
FROM usuario;

-- Podemos ver cuanta gente ocupa (mal) su dinero, por ejemplo, al ver balances negativos
-- Independiente de que su saldo inicial esté positivo, el pasado indica que gastan más de lo que ganan.

-- ROLLBACK TO avance3;

SAVEPOINT avance4;
-- SIMULAR UN ERROR DE INTEGRIDAD REFERENCIAL
-- Si intentamos transferir a una fila hija, no se debería poder actualizar ni añadir directamente
INSERT INTO transacciones (sender_id, receiver_id, moneda_id, monto, tipo_transaccion)
VALUES ('99999999-9', '12123123-7', 'CLP_ID', 50000, '0');

ROLLBACK TO avance4; -- lo dejo activo para no olvidar deshacer el error de prueba



DESCRIBE usuario;
DESCRIBE transacciones;
DESCRIBE monedas;
DESCRIBE contactos;















