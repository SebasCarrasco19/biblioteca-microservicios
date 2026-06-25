# Pruebas unitarias y de persistencia

## Objetivo

Comprobar automáticamente el comportamiento principal de los microservicios sin modificar su lógica de negocio ni depender de los datos reales de MySQL.

## Herramientas

- JUnit 5: ejecución y aserciones.
- Mockito: simulación de repositorios, clientes Feign y servicios.
- Spring Boot Test: soporte de pruebas Spring.
- MockMvc: pruebas de controladores HTTP.
- H2: base de datos temporal para pruebas de repositorios.

## Organización por módulo

Cada microservicio de negocio contiene ejemplos de:

```text
src/test/java/
├── .../service/...Test.java
├── .../controller/...Test.java
└── .../repository/...Test.java
```

Además, `src/test/resources/application.yml` reemplaza la conexión MySQL durante las pruebas por una base H2 en memoria y desactiva el registro en Eureka. Esto evita que `mvn clean install` necesite XAMPP, MySQL o Eureka para ejecutar las pruebas automáticas.

## Qué se prueba

| Módulo | Servicio | Controlador | Repositorio |
|---|---|---|---|
| user-service | búsqueda y usuario inexistente | listado HTTP | búsqueda por email |
| auth-service | rechazo de credenciales duplicadas | login HTTP | búsqueda de credencial por email |
| security-service | búsqueda de roles y validación de asignación | listado de roles | búsqueda por nombre |
| category-service | búsqueda y error 404 | listado de categorías | nombre sin distinguir mayúsculas |
| book-service | existencia de libro activo | listado de libros | existencia por ISBN |
| copy-service | disponibilidad de copia | listado de copias | búsqueda por estado |
| loan-service | búsqueda de préstamo | listado de préstamos | préstamo activo por copia |
| reservation-service | búsqueda de reserva | listado de reservas | reserva activa |
| fine-service | búsqueda de multa | listado de multas | multas por estado |
| notification-service | búsqueda de notificación | listado de notificaciones | notificaciones por usuario |

## Comandos

Ejecutar todas las pruebas y construir todo el proyecto:

```powershell
.\mvnw.cmd clean install
```

Ejecutar las pruebas de un único módulo:

```powershell
.\mvnw.cmd -pl user-service test
```

Ejecutar un módulo junto con las dependencias Maven necesarias dentro del reactor:

```powershell
.\mvnw.cmd -pl user-service -am test
```

Los informes de Surefire quedan dentro de cada módulo en:

```text
target/surefire-reports/
```
