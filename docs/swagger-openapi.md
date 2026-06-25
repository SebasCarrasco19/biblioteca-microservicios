# Swagger/OpenAPI documentado

## Objetivo

La documentación OpenAPI fue completada en los controladores de los microservicios para cumplir el punto de la rúbrica que exige `@Tag`, `@Operation` y `@ApiResponse` en los endpoints principales.

No se modificaron rutas, servicios, repositorios ni reglas de negocio. Los cambios son exclusivamente descriptivos y afectan la documentación generada por Swagger UI y `/v3/api-docs`.

## Anotaciones incorporadas

- `@Tag`: agrupa las operaciones bajo un nombre funcional visible en Swagger.
- `@Operation`: agrega un resumen corto y una descripción detallada a cada endpoint.
- `@ApiResponses` y `@ApiResponse`: muestran los códigos HTTP esperados y su significado.
- `@Parameter`: describe parámetros de ruta y encabezados, por ejemplo `id` y `X-User-Id`.

## Cobertura aplicada

- 11 controladores documentados.
- 73 endpoints documentados.
- Autenticación.
- Usuarios.
- Roles y asignaciones de roles.
- Categorías.
- Libros.
- Copias.
- Préstamos.
- Reservas.
- Multas.
- Notificaciones.

## Enlaces de Swagger UI

| Servicio | Puerto | Swagger UI |
|---|---:|---|
| user-service | 8082 | `http://localhost:8082/swagger-ui/index.html` |
| security-service | 8083 | `http://localhost:8083/swagger-ui/index.html` |
| auth-service | 8084 | `http://localhost:8084/swagger-ui/index.html` |
| category-service | 8085 | `http://localhost:8085/swagger-ui/index.html` |
| book-service | 8086 | `http://localhost:8086/swagger-ui/index.html` |
| copy-service | 8087 | `http://localhost:8087/swagger-ui/index.html` |
| loan-service | 8088 | `http://localhost:8088/swagger-ui/index.html` |
| reservation-service | 8089 | `http://localhost:8089/swagger-ui/index.html` |
| fine-service | 8090 | `http://localhost:8090/swagger-ui/index.html` |
| notification-service | 8091 | `http://localhost:8091/swagger-ui/index.html` |

## Cambio visual esperado

Al abrir Swagger UI se observará:

1. Un título y una descripción general definidos por cada `OpenApiConfig`.
2. Secciones con nombres claros, como **Usuarios**, **Libros**, **Préstamos** o **Multas**, en lugar de nombres automáticos derivados de las clases Java.
3. Cada operación mostrará un resumen descriptivo junto a la ruta.
4. Al expandir un endpoint se verá una descripción de su comportamiento.
5. La sección **Responses** mostrará códigos como `200`, `201`, `400`, `401`, `403`, `404`, `409` y `500`, con una explicación legible.
6. Los parámetros de ruta y encabezados mostrarán su significado, ejemplo y condición de requerido.

## Validación recomendada

1. Iniciar Eureka Server.
2. Iniciar el microservicio que se desea revisar.
3. Abrir su enlace Swagger UI.
4. Confirmar que aparezca la sección nombrada mediante `@Tag`.
5. Expandir endpoints GET, POST, PUT, PATCH y DELETE.
6. Revisar el resumen, descripción, parámetros y códigos de respuesta.
7. Ejecutar una ruta con **Try it out** cuando las dependencias requeridas estén disponibles.
