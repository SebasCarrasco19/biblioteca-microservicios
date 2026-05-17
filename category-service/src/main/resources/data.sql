INSERT INTO categories (id, nombre, descripcion, estado, fecha_creacion)
SELECT 1, 'General', 'Categoria inicial', 'ACTIVO', NOW()
WHERE NOT EXISTS (SELECT 1 FROM categories WHERE id = 1);
