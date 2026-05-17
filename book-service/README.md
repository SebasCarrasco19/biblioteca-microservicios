# book-service

Microservicio encargado solo de la ficha bibliografica del libro.

Maneja:
- titulo
- autor
- ISBN
- editorial
- ano de publicacion
- categoria
- estado del libro

No maneja copias, stock, disponibilidad fisica, reservas ni prestamos.

## Endpoints
- `POST /api/books`
- `GET /api/books`
- `GET /api/books/{id}`
- `GET /api/books/{id}/exists`
- `PUT /api/books/{id}`
- `DELETE /api/books/{id}`
- `PATCH /api/books/{id}/activar`
