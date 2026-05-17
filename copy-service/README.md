# copy-service

Microservicio para gestionar ejemplares fisicos de libros.

Responsabilidad:
- validar que el libro exista y este ACTIVO en book-service
- crear y actualizar copias fisicas asociadas a `bookId`
- gestionar estado operativo de la copia: `DISPONIBLE`, `PRESTADO`, `RESERVADO`, `DANADO`
- gestionar estado logico: `ACTIVO`, `INACTIVO`

No crea libros, no administra categorias, no registra prestamos completos, no registra reservas completas y no calcula multas.

## Endpoints
- `POST /api/copies`
- `GET /api/copies`
- `GET /api/copies/{id}`
- `GET /api/copies/{id}/available`
- `PUT /api/copies/{id}`
- `DELETE /api/copies/{id}`
- `PATCH /api/copies/{id}/activate`
- `PATCH /api/copies/{id}/reserve`
- `PATCH /api/copies/{id}/release`
- `PATCH /api/copies/{id}/borrow`
- `PATCH /api/copies/{id}/return`
