INSERT INTO roles (id, nombre, descripcion, estado, fecha_creacion)
SELECT 1, 'ADMIN', 'Administrador del sistema', 'ACTIVO', NOW()
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE id = 1);

INSERT INTO roles (id, nombre, descripcion, estado, fecha_creacion)
SELECT 2, 'BIBLIOTECARIO', 'Bibliotecario', 'ACTIVO', NOW()
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE id = 2);

INSERT INTO usuarios_roles (id, user_id, role_id, estado, fecha_asignacion)
SELECT 1, 1, 1, 'ACTIVO', NOW()
WHERE NOT EXISTS (SELECT 1 FROM usuarios_roles WHERE user_id = 1 AND role_id = 1);
