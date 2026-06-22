# Biblioteca Microservicios — Maven Multi-Módulo

Este repositorio ahora se administra desde un único proyecto padre Maven. La lógica, los endpoints, las bases de datos y la comunicación entre microservicios se mantienen.

## Abrir y compilar

Abra **solamente esta carpeta raíz** en VSCode. Verifique Java y Maven:

```bash
java -version
mvn -version
```

Primera compilación recomendada:

```bash
mvn clean install -DskipTests
```

Alternativa con Maven Wrapper en Windows:

```powershell
.\mvnw.cmd clean install -DskipTests
```

## Swagger/OpenAPI

Los diez microservicios de negocio incluyen Swagger UI. Con el servicio iniciado, utilice:

- `http://localhost:<puerto>/swagger-ui.html`
- `http://localhost:<puerto>/v3/api-docs`

La documentación adicional se encuentra en `docs/`.

## Pruebas automáticas — Etapa 4

Se incorporaron pruebas con JUnit 5, Mockito, MockMvc, Spring Boot Test y H2. Los diez microservicios de negocio incluyen pruebas de servicio, controlador y repositorio.

Después de validar la estructura con `-DskipTests`, la construcción completa debe ejecutarse con:

```powershell
.\mvnw.cmd clean install
```

Las pruebas usan H2 en memoria y desactivan Eureka durante el entorno de test, por lo que no necesitan MySQL ni los otros microservicios para ejecutarse. Los documentos originales de las cuatro etapas están en `docs/documentación/etapas/`, y el detalle técnico se encuentra en `docs/documentación/pruebas-unitarias.md`.

---

# Sistema de Biblioteca con Arquitectura de Microservicios

## 1. Presentación general

Este proyecto corresponde a un sistema de biblioteca desarrollado con arquitectura de microservicios para la asignatura Desarrollo Fullstack I.

El sistema permite gestionar usuarios, autenticación, roles, categorías, libros, copias físicas, préstamos, reservas, multas y notificaciones. La solución fue construida separando las responsabilidades principales en distintos microservicios, con el objetivo de evitar una aplicación monolítica y permitir que cada funcionalidad pueda ser desarrollada, ejecutada y probada de forma independiente.

## 2. Objetivo del sistema

El objetivo principal del sistema es administrar los procesos básicos de una biblioteca mediante servicios independientes que se comunican entre sí.

El sistema permite:

- Registrar y administrar usuarios.
- Registrar credenciales e iniciar sesión.
- Crear y asignar roles a usuarios.
- Registrar categorías de libros.
- Registrar libros asociados a categorías.
- Registrar copias físicas asociadas a libros.
- Crear préstamos de copias disponibles.
- Crear reservas de copias.
- Registrar multas asociadas a préstamos.
- Registrar notificaciones para usuarios.

## 3. Problema que resuelve

En una biblioteca existen distintas áreas de gestión: usuarios, libros, copias, préstamos, reservas, multas y notificaciones. Si todo esto se desarrolla en una sola aplicación, el sistema se vuelve más difícil de mantener, probar y escalar.

Este proyecto resuelve ese problema dividiendo la lógica en microservicios independientes. Cada servicio tiene una responsabilidad específica y se comunica con otros servicios cuando necesita validar información o ejecutar acciones relacionadas.

## 4. Arquitectura del sistema

El sistema está compuesto por los siguientes componentes:

| Componente | Responsabilidad |
|---|---|
| eureka-server | Servidor de descubrimiento de servicios. Permite registrar y visualizar los microservicios activos. |
| api-gateway | Punto único de entrada al sistema. Redirige las solicitudes hacia cada microservicio. |
| user-service | Gestión de usuarios. |
| auth-service | Registro de credenciales e inicio de sesión. |
| security-service | Gestión de roles y asignación de roles a usuarios. |
| category-service | Gestión de categorías de libros. |
| book-service | Gestión de libros. Valida categorías y permisos. |
| copy-service | Gestión de copias físicas o ejemplares. Valida libros existentes. |
| loan-service | Gestión de préstamos. Valida usuarios y copias. |
| reservation-service | Gestión de reservas. Valida usuarios y copias. |
| fine-service | Gestión de multas asociadas a préstamos. |
| notification-service | Gestión de notificaciones para usuarios. |

## 5. Comunicación entre microservicios

Los microservicios se comunican principalmente mediante OpenFeign, utilizando los nombres registrados en Eureka.

Ejemplos de comunicación:

- auth-service consulta a user-service para validar que un usuario exista antes de registrar credenciales.
- security-service administra roles y asignaciones de roles asociados a usuarios.
- book-service consulta a category-service para validar que una categoría exista antes de crear un libro.
- copy-service consulta a book-service para validar que un libro exista antes de crear una copia.
- loan-service consulta a user-service y copy-service antes de crear un préstamo.
- reservation-service consulta a user-service y copy-service antes de crear una reserva.
- fine-service valida usuario y préstamo antes de registrar una multa.
- notification-service permite registrar avisos asociados a usuarios.

## 6. API Gateway

El sistema utiliza api-gateway como punto único de entrada.

En vez de consumir directamente cada microservicio por su puerto interno, las pruebas se realizan mediante:

http://localhost:8080

Ejemplos:

GET http://localhost:8080/api/users
GET http://localhost:8080/api/books
GET http://localhost:8080/api/loans

El Gateway redirige internamente la solicitud hacia el microservicio correspondiente usando Eureka.

## 7. Eureka Server

Eureka Server permite visualizar los servicios registrados y comprobar que se encuentran activos.

URL de Eureka:

http://localhost:8761

En Eureka deben aparecer los microservicios en estado UP.

## 8. Tecnologías utilizadas

- Java 21
- Spring Boot 3.5.14
- Spring Cloud 2025.0.2
- Spring Web
- Spring Data JPA
- Spring Validation
- Spring Cloud OpenFeign
- Spring Cloud Eureka
- Spring Cloud Gateway WebFlux / Netty
- MySQL
- XAMPP
- Maven
- Postman
- Git y GitHub
- VS Code

## 9. Puertos utilizados

| Servicio | Puerto |
|---|---|
| Eureka Server | 8761 |
| API Gateway | 8080 |
| User Service | 8082 |
| Security Service | 8083 |
| Auth Service | 8084 |
| Category Service | 8085 |
| Book Service | 8086 |
| Copy Service | 8087 |
| Loan Service | 8088 |
| Reservation Service | 8089 |
| Fine Service | 8090 |
| Notification Service | 8091 |

## 10. Bases de datos

Cada microservicio utiliza su propia base de datos en MySQL.

Ejemplo de bases utilizadas:

| Microservicio | Base de datos |
|---|---|
| user-service | db_users |
| auth-service | db_auth |
| security-service | db_security |
| category-service | db_categories |
| book-service | db_books |
| copy-service | db_copies |
| loan-service | db_loans |
| reservation-service | db_reservations |
| fine-service | db_fines |
| notification-service | db_notifications |

Las bases se crean automáticamente mediante la configuración:

createDatabaseIfNotExist=true

## 11. Requisitos previos

Antes de ejecutar el proyecto se debe tener instalado:

- Java 21
- Maven
- XAMPP
- MySQL activo en XAMPP
- Postman
- Git
- VS Code o IDE compatible

Importante:

En XAMPP solo es necesario iniciar MySQL.
No es necesario iniciar Tomcat desde XAMPP.

## 12. Orden recomendado de ejecución

Para probar correctamente el proyecto, se recomienda levantar los servicios en este orden:

1. XAMPP / MySQL
2. eureka-server
3. user-service
4. security-service
5. auth-service
6. category-service
7. book-service
8. copy-service
9. loan-service
10. reservation-service
11. fine-service
12. notification-service
13. api-gateway

Después de iniciar los servicios, revisar Eureka:

http://localhost:8761

Todos los servicios deben aparecer registrados y en estado UP.

## 13. Ejecución de un microservicio

Para ejecutar un microservicio desde terminal:

cd nombre-del-microservicio
./mvnw spring-boot:run

En Windows:

cd nombre-del-microservicio
.\mvnw.cmd spring-boot:run

Para compilar un microservicio:

.\mvnw.cmd clean package -DskipTests

## 14. Endpoints principales

### User Service

POST   /api/users
GET    /api/users
GET    /api/users/{id}
PUT    /api/users/{id}
DELETE /api/users/{id}
PATCH  /api/users/{id}/activar

### Auth Service

POST /api/auth/register
POST /api/auth/login

### Security Service

POST   /api/roles
GET    /api/roles
GET    /api/roles/{id}
PUT    /api/roles/{id}
DELETE /api/roles/{id}
PATCH  /api/roles/{id}/activar

POST   /api/user-roles
GET    /api/user-roles/user/{userId}
GET    /api/user-roles/validate/{userId}/{roleName}
DELETE /api/user-roles/{id}

### Category Service

POST   /api/categories
GET    /api/categories
GET    /api/categories/{id}
PUT    /api/categories/{id}
DELETE /api/categories/{id}
PATCH  /api/categories/{id}/activate

### Book Service

POST   /api/books
GET    /api/books
GET    /api/books/{id}
GET    /api/books/{id}/exists
PUT    /api/books/{id}
DELETE /api/books/{id}
PATCH  /api/books/{id}/activate

### Copy Service

POST   /api/copies
GET    /api/copies
GET    /api/copies/{id}
GET    /api/copies/{id}/available
PUT    /api/copies/{id}
DELETE /api/copies/{id}
PATCH  /api/copies/{id}/activate
PATCH  /api/copies/{id}/reserve
PATCH  /api/copies/{id}/release
PATCH  /api/copies/{id}/borrow
PATCH  /api/copies/{id}/return

### Loan Service

POST  /api/loans
GET   /api/loans
GET   /api/loans/{id}
GET   /api/loans/user/{userId}
PATCH /api/loans/{id}/return
PATCH /api/loans/{id}/cancel
PATCH /api/loans/{id}/overdue

### Reservation Service

POST   /api/reservations
GET    /api/reservations
GET    /api/reservations/{id}
GET    /api/reservations/user/{userId}
DELETE /api/reservations/{id}
PATCH  /api/reservations/{id}/activar
PATCH  /api/reservations/{id}/expirar

### Fine Service

POST   /api/fines
GET    /api/fines
GET    /api/fines/{id}
GET    /api/fines/user/{userId}
GET    /api/fines/loan/{loanId}
GET    /api/fines/pending
PUT    /api/fines/{id}/pay
DELETE /api/fines/{id}

### Notification Service

POST   /api/notifications
GET    /api/notifications
GET    /api/notifications/{id}
GET    /api/notifications/user/{userId}
PATCH  /api/notifications/{id}/send
PATCH  /api/notifications/{id}/read
DELETE /api/notifications/{id}

## 15. Flujo funcional recomendado para pruebas en Postman

El flujo recomendado de prueba es:

1. Crear usuario.
2. Crear rol ADMIN.
3. Asignar rol ADMIN al usuario.
4. Validar que el usuario tenga rol ADMIN.
5. Crear categoría.
6. Crear libro asociado a la categoría.
7. Crear copia asociada al libro.
8. Crear préstamo asociado a usuario y copia.
9. Verificar que la copia cambie a PRESTADO.
10. Devolver préstamo.
11. Verificar que la copia vuelva a DISPONIBLE.
12. Crear reserva.
13. Crear multa.
14. Crear notificación.

## 16. Validaciones y manejo de errores

El sistema implementa validaciones mediante DTOs y Bean Validation.

Ejemplos de errores controlados:

- 400 Bad Request: campos vacíos o formato inválido.
- 404 Not Found: recurso inexistente.
- 409 Conflict: duplicados o estados inválidos.
- 403 Forbidden: usuario sin permisos suficientes.
- 503 Service Unavailable: error de comunicación con otro microservicio.

Ejemplos:

- Crear usuario con email inválido devuelve 400.
- Buscar una categoría inexistente devuelve 404.
- Crear un libro con ISBN duplicado devuelve 409.
- Crear un libro sin rol ADMIN o BIBLIOTECARIO devuelve 403.

## 17. Evidencias del proyecto

El repositorio incluye una carpeta para evidencias de entrega:

entrega-video/

En esa carpeta se puede subir el video de demostración técnica solicitado por la evaluación.

## 18. Integrantes

- Sebastian Carrasco
- Daniel Perez
- Simon Ojeda

## 19. Distribución de responsabilidades

| Integrante | Responsabilidades |
|---|---|
| Sebastian Carrasco | Eureka Server, API Gateway, user-service, auth-service y security-service. |
| Daniel Perez | book-service, copy-service y reservation-service. |
| Simon Ojeda | category-service, loan-service, fine-service y notification-service. |

## 20. Estado actual del proyecto

El proyecto cuenta con:

- Arquitectura de microservicios.
- Eureka Server funcionando.
- API Gateway operativo.
- Microservicios registrados en Eureka.
- CRUD funcionales.
- Comunicación entre servicios.
- Validaciones con DTOs.
- Manejo de errores.
- Pruebas mediante Postman.
- Bases de datos independientes por microservicio.

## 21. Mejoras futuras

Como mejoras futuras se podrían implementar:

- Validación completa de JWT en API Gateway.
- Mayor control de permisos por endpoint.
- Documentación Swagger por microservicio.
- Automatización de pruebas.
- Integración más avanzada entre fine-service y notification-service.
- Registro centralizado de logs.
- Contenedores Docker para levantar el sistema completo con mayor facilidad.
