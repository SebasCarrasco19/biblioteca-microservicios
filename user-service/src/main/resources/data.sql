INSERT INTO usuarios (id, nombre, apellido, email, telefono, estado, fecha_creacion)
SELECT 1, 'Admin', 'Inicial', 'admin@biblioteca.local', '900000001', 'ACTIVO', NOW()
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE id = 1);
