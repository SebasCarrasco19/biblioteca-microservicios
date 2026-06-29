# Biblioteca Microservicios — Maven Multi-Módulo

Sistema de biblioteca desarrollado con **Java 21, Spring Boot, Spring Cloud, Maven Multi-Módulo, MySQL, Eureka Server, API Gateway y Docker Compose** para la asignatura **Desarrollo FullStack I**.

## 🚀 Enlaces de entrega

| Entrega | Contenido | Enlace |
|---|---|---|
| **📦 Versión nativa**<br>JAR + BAT | Archivo `.zip` con la carpeta `apps/`, los 12 archivos `.jar`, `arrancar-nativo.bat`, `detener-nativo.bat` y la documentación de ejecución. | [Descargar versión nativa](https://drive.google.com/file/d/1bULOkHL3EOhEFXhALF8dU7pzyS027YcE/view?usp=sharing) |
| **🐳 Versión Docker** | Archivo `.zip` con los 12 archivos `.jar`, `docker-compose.yml`, variables de entorno, scripts de administración y documentación de despliegue. | [Descargar versión Docker](https://drive.google.com/file/d/1WIdNUZzJBcoLepibYpvanQ0tfjRMYiM_/view?usp=sharing) |
| **🎥 Video de defensa técnica** | Evidencia del funcionamiento del sistema, las pruebas unitarias y el aporte técnico de cada integrante. Duración ideal: **15 minutos**. Duración máxima: **18 minutos**. | [Ver video de defensa](https://drive.google.com/drive/folders/1zjMHkm9baMPnMFc-ASUDExCJuqw5yWFJ) |

> La entrega incluye el archivo `subtitulos-video.txt` con el contenido hablado durante la defensa técnica.

---

## 1. Descripción general

El sistema permite gestionar:

- usuarios;
- credenciales e inicio de sesión;
- roles y asignaciones de roles;
- categorías;
- libros;
- copias físicas;
- préstamos;
- reservas;
- multas;
- notificaciones.

La solución separa cada responsabilidad en un microservicio independiente. Los servicios se descubren mediante **Eureka**, se consumen a través de **API Gateway** y se comunican mediante **REST y OpenFeign**.

## 2. Arquitectura

| Componente | Responsabilidad | Puerto |
|---|---|---:|
| `eureka-server` | Descubrimiento y registro de servicios | 8761 |
| `api-gateway` | Punto único de entrada y enrutamiento | 8080 |
| `user-service` | Gestión de usuarios | 8082 |
| `security-service` | Roles y asignación de roles | 8083 |
| `auth-service` | Registro de credenciales, login y JWT | 8084 |
| `category-service` | Gestión de categorías | 8085 |
| `book-service` | Gestión de libros | 8086 |
| `copy-service` | Gestión de ejemplares físicos | 8087 |
| `loan-service` | Gestión de préstamos | 8088 |
| `reservation-service` | Gestión de reservas | 8089 |
| `fine-service` | Gestión de multas | 8090 |
| `notification-service` | Gestión de notificaciones | 8091 |

### Orden jerárquico de arranque

```text
1. MySQL
2. Eureka Server
3. Microservicios de negocio
4. API Gateway
```

Eureka debe estar disponible antes de los microservicios, y API Gateway debe iniciarse al final para enrutar hacia servicios ya registrados.

## 3. Tecnologías utilizadas

- Java 21
- Spring Boot
- Spring Cloud Eureka
- Spring Cloud Gateway WebFlux
- Spring Cloud OpenFeign
- Spring Data JPA
- Bean Validation
- MySQL 8
- Maven Multi-Módulo
- JUnit 5
- Mockito
- MockMvc
- H2
- JaCoCo
- Swagger / OpenAPI
- Docker Desktop y Docker Compose
- Git y GitHub

---

## Puesta en marcha

## 4. Versión nativa — JAR + BAT

### Contenido del paquete

```text
biblioteca-deploy-nativo/
├── apps/                         # 12 archivos JAR
├── logs/
├── arrancar-nativo.bat
├── detener-nativo.bat
└── README-EJECUCION-NATIVA.md
```

### Requisitos

- Windows 10 u 11
- Java 21
- MySQL disponible en `localhost:3307`
- Usuario local configurado: `root`
- Contraseña local configurada: vacía

### Ejecución

1. Descargar y extraer `biblioteca-deploy-nativo.zip`.
2. Iniciar MySQL en XAMPP usando el puerto `3307`.
3. Ejecutar con doble clic:

```text
arrancar-nativo.bat
```

El script valida Java, MySQL y los 12 JAR, y ejecuta automáticamente:

```text
1. eureka-server
2. user-service
3. security-service
4. auth-service
5. category-service
6. book-service
7. copy-service
8. loan-service
9. reservation-service
10. fine-service
11. notification-service
12. api-gateway
```

El orden lógico aplicado por el script es:

```text
Eureka Server → Microservicios → API Gateway
```

### Verificación

- Eureka: `http://localhost:8761`
- Gateway: `http://localhost:8080/api/categories`
- Swagger User: `http://localhost:8082/swagger-ui/index.html`

En Eureka deben aparecer los diez microservicios y API Gateway con estado `UP`.

### Detención

Ejecutar:

```text
detener-nativo.bat
```

---

## 5. Versión Docker

### Contenido del paquete

```text
biblioteca-deploy-docker/
├── apps/                         # 12 archivos JAR
├── backups/
├── config/
├── docs/
│   └── init.sql
├── .env
├── docker-compose.yml
├── arrancar-docker.bat
├── detener-docker.bat
├── ver-logs.bat
├── backup-db.bat
├── restaurar-db.bat
└── README-DESPLIEGUE-DOCKER.md
```

### Requisitos

- Docker Desktop
- Docker Engine en estado `running`
- WSL 2 habilitado en Windows
- Puerto `3307` disponible

> [!WARNING]
> Para ejecutar la versión Docker, detenga MySQL de XAMPP si está utilizando el puerto `3307`.

### Ejecución

1. Descargar y extraer `biblioteca-deploy-docker.zip`.
2. Abrir Docker Desktop y esperar a que Docker Engine esté funcionando.
3. Ejecutar:

```text
arrancar-docker.bat
```

El despliegue levanta 13 contenedores:

- 1 contenedor MySQL;
- 1 Eureka Server;
- 10 microservicios de negocio;
- 1 API Gateway.

### Verificación

Desde PowerShell, dentro de la carpeta Docker:

```powershell
docker compose ps
```

MySQL debe aparecer como `healthy` y los demás contenedores como `Up`.

Enlaces principales:

- Eureka: `http://localhost:8761`
- Gateway: `http://localhost:8080/api/categories`
- Swagger User: `http://localhost:8082/swagger-ui/index.html`

### Detención

Ejecutar:

```text
detener-docker.bat
```

El comando conserva el volumen de MySQL. No utilizar `docker compose down -v` salvo que se quiera eliminar completamente la información persistida.

---

## Calidad, pruebas y documentación

## 6. Compilación del proyecto

Abra únicamente la carpeta raíz del repositorio, donde se encuentra el `pom.xml` padre.

Verificar Java y Maven:

```powershell
java -version
.\mvnw.cmd -version
```

En Windows, el comando recomendado es:

```powershell
.\mvnw.cmd clean install
```

También puede validarse la fase de pruebas y cobertura con:

```powershell
.\mvnw.cmd clean verify
```

El resultado esperado es:

```text
BUILD SUCCESS
```

> [!NOTE]
> `-DskipTests` solo debe utilizarse para una compilación rápida de diagnóstico. La validación final debe ejecutarse sin omitir las pruebas.

## 7. Pruebas unitarias

El proyecto incluye pruebas con:

- JUnit 5;
- Mockito;
- MockMvc;
- Spring Boot Test;
- H2 en memoria;
- JaCoCo para medir cobertura.

Se prueban principalmente:

- lógica de negocio de los Service;
- respuestas y códigos HTTP de los Controller;
- consultas de Repository;
- excepciones;
- validaciones;
- clientes Feign simulados;
- casos positivos y negativos.

La suite completa puede ejecutarse con:

```powershell
.\mvnw.cmd clean install
```

También puede validarse con:

```powershell
.\mvnw.cmd clean verify
```

La construcción validada debe terminar sin fallos, errores ni pruebas omitidas. Los reportes JaCoCo se generan por módulo en:

```text
<servicio>/target/site/jacoco/index.html
```

## 8. Swagger / OpenAPI

Los controladores están documentados mediante anotaciones OpenAPI, incluyendo descripciones, parámetros y respuestas HTTP.

### Swagger UI por microservicio

| Servicio | Swagger UI |
|---|---|
| User | `http://localhost:8082/swagger-ui/index.html` |
| Security | `http://localhost:8083/swagger-ui/index.html` |
| Auth | `http://localhost:8084/swagger-ui/index.html` |
| Category | `http://localhost:8085/swagger-ui/index.html` |
| Book | `http://localhost:8086/swagger-ui/index.html` |
| Copy | `http://localhost:8087/swagger-ui/index.html` |
| Loan | `http://localhost:8088/swagger-ui/index.html` |
| Reservation | `http://localhost:8089/swagger-ui/index.html` |
| Fine | `http://localhost:8090/swagger-ui/index.html` |
| Notification | `http://localhost:8091/swagger-ui/index.html` |

El documento OpenAPI en formato JSON se encuentra en:

```text
http://localhost:<puerto>/v3/api-docs
```

---

## Configuración y funcionamiento

## 9. Bases de datos

Cada microservicio de negocio utiliza una base independiente:

| Microservicio | Base de datos |
|---|---|
| `user-service` | `db_users` |
| `security-service` | `db_security` |
| `auth-service` | `db_auth` |
| `category-service` | `db_categories` |
| `book-service` | `db_books` |
| `copy-service` | `db_copies` |
| `loan-service` | `db_loans` |
| `reservation-service` | `db_reservations` |
| `fine-service` | `db_fines` |
| `notification-service` | `db_notifications` |

### Entorno nativo

```text
MySQL: localhost:3307
Eureka: localhost:8761
```

### Entorno Docker

```text
MySQL interno: mysql-db:3306
Eureka interno: eureka-server:8761
MySQL expuesto al host: 3307
```

Dentro de Docker no se utiliza `localhost` para la comunicación entre contenedores.

## 10. Comunicación entre microservicios

Ejemplos principales:

- `auth-service` consulta a `user-service`;
- `book-service` consulta a `category-service` y `security-service`;
- `copy-service` consulta a `book-service`;
- `loan-service` consulta a `user-service` y `copy-service`;
- `reservation-service` consulta a `user-service` y `copy-service`;
- `fine-service` valida usuarios y préstamos;
- los servicios se descubren por nombre mediante Eureka.

## 11. API Gateway

API Gateway funciona en:

```text
http://localhost:8080
```

La raíz `/` no tiene una página web configurada y puede responder `404`. Se deben utilizar rutas reales, por ejemplo:

```text
GET http://localhost:8080/api/users
GET http://localhost:8080/api/categories
GET http://localhost:8080/api/books
GET http://localhost:8080/api/loans
```

Una respuesta `200`, `401` o `403` confirma que la ruta está siendo atendida. Un `503` o `Connection refused` indica un problema de disponibilidad o enrutamiento.

## 12. Validaciones y manejo de errores

El sistema utiliza DTO, Bean Validation y excepciones centralizadas.

Respuestas habituales:

- `400 Bad Request`: campos inválidos;
- `401 Unauthorized`: autenticación requerida;
- `403 Forbidden`: permisos insuficientes;
- `404 Not Found`: recurso inexistente;
- `409 Conflict`: duplicado o transición inválida;
- `503 Service Unavailable`: fallo de comunicación entre servicios.

---

## Equipo

## 13. Integrantes y responsabilidades

| Integrante | Responsabilidades |
|---|---|
| Sebastián Carrasco | Eureka Server, API Gateway, `user-service`, `auth-service` y `security-service` |
| Daniel Pérez | `book-service`, `copy-service` y `reservation-service` |
| Simón Ojeda | `category-service`, `loan-service`, `fine-service` y `notification-service` |
