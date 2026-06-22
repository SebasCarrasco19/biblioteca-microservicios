# Inventario de endpoints

Este documento se generó a partir de los controladores existentes. El uso actual de `X-User-Id` se mantiene sin cambios y Swagger lo muestra como encabezado únicamente donde el controlador ya lo solicita.

## Acceso a Swagger

| Microservicio | Puerto | Swagger UI |
|---|---:|---|
| `user-service` | `8082` | `http://localhost:8082/swagger-ui.html` |
| `auth-service` | `8084` | `http://localhost:8084/swagger-ui.html` |
| `security-service` | `8083` | `http://localhost:8083/swagger-ui.html` |
| `category-service` | `8085` | `http://localhost:8085/swagger-ui.html` |
| `book-service` | `8086` | `http://localhost:8086/swagger-ui.html` |
| `copy-service` | `8087` | `http://localhost:8087/swagger-ui.html` |
| `loan-service` | `8088` | `http://localhost:8088/swagger-ui.html` |
| `reservation-service` | `8089` | `http://localhost:8089/swagger-ui.html` |
| `fine-service` | `8090` | `http://localhost:8090/swagger-ui.html` |
| `notification-service` | `8091` | `http://localhost:8091/swagger-ui.html` |

## user-service — puerto 8082

- Swagger UI: `http://localhost:8082/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8082/v3/api-docs`

| Método | Ruta | Operación | Header adicional |
|---|---|---|---|
| `POST` | `/api/users` | `crearUsuario` | `—` |
| `GET` | `/api/users` | `listarUsuarios` | `—` |
| `GET` | `/api/users/{id}` | `buscarPorId` | `—` |
| `PUT` | `/api/users/{id}` | `actualizarUsuario` | `—` |
| `DELETE` | `/api/users/{id}` | `desactivarUsuario` | `—` |
| `PATCH` | `/api/users/{id}/activar` | `activarUsuario` | `—` |

## auth-service — puerto 8084

- Swagger UI: `http://localhost:8084/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8084/v3/api-docs`

| Método | Ruta | Operación | Header adicional |
|---|---|---|---|
| `POST` | `/api/auth/register` | `registrarCredenciales` | `—` |
| `POST` | `/api/auth/login` | `login` | `—` |

## security-service — puerto 8083

- Swagger UI: `http://localhost:8083/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8083/v3/api-docs`

| Método | Ruta | Operación | Header adicional |
|---|---|---|---|
| `POST` | `/api/roles` | `crearRol` | `—` |
| `GET` | `/api/roles` | `listarRoles` | `—` |
| `GET` | `/api/roles/{id}` | `buscarRolPorId` | `—` |
| `PUT` | `/api/roles/{id}` | `actualizarRol` | `—` |
| `DELETE` | `/api/roles/{id}` | `desactivarRol` | `—` |
| `PATCH` | `/api/roles/{id}/activar` | `activarRol` | `—` |
| `POST` | `/api/user-roles` | `asignarRolAUsuario` | `—` |
| `GET` | `/api/user-roles/user/{userId}` | `listarRolesPorUsuario` | `—` |
| `GET` | `/api/user-roles/validate/{userId}/{roleName}` | `validarRolUsuario` | `—` |
| `DELETE` | `/api/user-roles/{id}` | `desactivarAsignacion` | `—` |

## category-service — puerto 8085

- Swagger UI: `http://localhost:8085/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8085/v3/api-docs`

| Método | Ruta | Operación | Header adicional |
|---|---|---|---|
| `GET` | `/api/categories` | `getAllCategories` | `—` |
| `GET` | `/api/categories/{id}` | `getCategoryById` | `—` |
| `POST` | `/api/categories` | `createCategory` | `—` |
| `PUT` | `/api/categories/{id}` | `updateCategory` | `—` |
| `DELETE` | `/api/categories/{id}` | `deleteCategory` | `—` |
| `PATCH` | `/api/categories/{id}/activate` | `activateCategory` | `—` |

## book-service — puerto 8086

- Swagger UI: `http://localhost:8086/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8086/v3/api-docs`

| Método | Ruta | Operación | Header adicional |
|---|---|---|---|
| `POST` | `/api/books` | `createBook` | `X-User-Id` |
| `GET` | `/api/books` | `getBooks` | `—` |
| `GET` | `/api/books/{id}` | `getBookById` | `—` |
| `GET` | `/api/books/{id}/exists` | `exists` | `—` |
| `PUT` | `/api/books/{id}` | `updateBook` | `X-User-Id` |
| `DELETE` | `/api/books/{id}` | `deactivateBook` | `X-User-Id` |
| `PATCH` | `/api/books/{id}/activate` | `activateBook` | `X-User-Id` |

## copy-service — puerto 8087

- Swagger UI: `http://localhost:8087/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8087/v3/api-docs`

| Método | Ruta | Operación | Header adicional |
|---|---|---|---|
| `POST` | `/api/copies` | `createCopy` | `—` |
| `GET` | `/api/copies` | `getCopies` | `—` |
| `GET` | `/api/copies/{id}` | `getCopyById` | `—` |
| `GET` | `/api/copies/{id}/available` | `available` | `—` |
| `PUT` | `/api/copies/{id}` | `updateCopy` | `—` |
| `DELETE` | `/api/copies/{id}` | `deactivateCopy` | `—` |
| `PATCH` | `/api/copies/{id}/activate` | `activateCopy` | `—` |
| `PATCH` | `/api/copies/{id}/reserve` | `reserveCopy` | `—` |
| `PATCH` | `/api/copies/{id}/release` | `releaseCopy` | `—` |
| `PATCH` | `/api/copies/{id}/borrow` | `borrowCopy` | `—` |
| `PATCH` | `/api/copies/{id}/return` | `returnCopy` | `—` |
| `POST` | `/api/copies/{id}/reserve` | `reserveCopyPost` | `—` |
| `POST` | `/api/copies/{id}/release` | `releaseCopyPost` | `—` |

## loan-service — puerto 8088

- Swagger UI: `http://localhost:8088/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8088/v3/api-docs`

| Método | Ruta | Operación | Header adicional |
|---|---|---|---|
| `POST` | `/api/loans` | `createLoan` | `X-User-Id` |
| `GET` | `/api/loans` | `getLoans` | `—` |
| `GET` | `/api/loans/{id}` | `getLoanById` | `—` |
| `GET` | `/api/loans/user/{userId}` | `getLoansByUser` | `—` |
| `PATCH` | `/api/loans/{id}/return` | `returnLoan` | `X-User-Id` |
| `PATCH` | `/api/loans/{id}/cancel` | `cancelLoan` | `X-User-Id` |
| `PATCH` | `/api/loans/{id}/overdue` | `markLoanAsOverdue` | `—` |

## reservation-service — puerto 8089

- Swagger UI: `http://localhost:8089/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8089/v3/api-docs`

| Método | Ruta | Operación | Header adicional |
|---|---|---|---|
| `POST` | `/api/reservations` | `createReservation` | `—` |
| `GET` | `/api/reservations` | `listReservations` | `—` |
| `GET` | `/api/reservations/{id}` | `getById` | `—` |
| `GET` | `/api/reservations/user/{userId}` | `listByUser` | `—` |
| `DELETE` | `/api/reservations/{id}` | `cancelReservation` | `—` |
| `PATCH` | `/api/reservations/{id}/activar` | `activateReservation` | `—` |
| `PATCH` | `/api/reservations/{id}/expirar` | `expireReservation` | `—` |

## fine-service — puerto 8090

- Swagger UI: `http://localhost:8090/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8090/v3/api-docs`

| Método | Ruta | Operación | Header adicional |
|---|---|---|---|
| `GET` | `/api/fines` | `getAllFines` | `—` |
| `GET` | `/api/fines/{id}` | `getFineById` | `—` |
| `GET` | `/api/fines/user/{userId}` | `getFinesByUserId` | `—` |
| `GET` | `/api/fines/loan/{loanId}` | `getFinesByLoanId` | `—` |
| `GET` | `/api/fines/pending` | `getPendingFines` | `—` |
| `POST` | `/api/fines` | `createFine` | `—` |
| `PUT` | `/api/fines/{id}/pay` | `payFine` | `—` |
| `DELETE` | `/api/fines/{id}` | `cancelFine` | `—` |

## notification-service — puerto 8091

- Swagger UI: `http://localhost:8091/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8091/v3/api-docs`

| Método | Ruta | Operación | Header adicional |
|---|---|---|---|
| `POST` | `/api/notifications` | `create` | `—` |
| `GET` | `/api/notifications` | `getAll` | `—` |
| `GET` | `/api/notifications/{id}` | `getById` | `—` |
| `GET` | `/api/notifications/user/{userId}` | `getByUserId` | `—` |
| `PATCH` | `/api/notifications/{id}/send` | `send` | `—` |
| `PATCH` | `/api/notifications/{id}/read` | `read` | `—` |
| `DELETE` | `/api/notifications/{id}` | `cancel` | `—` |
