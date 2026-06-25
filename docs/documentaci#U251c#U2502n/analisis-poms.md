# Análisis Maven y propuesta aplicada

## Inventario

El reactor Maven contiene 12 módulos: dos componentes de infraestructura y diez microservicios de negocio.

| Tipo | Módulos |
|---|---|
| Infraestructura | `eureka-server`, `api-gateway` |
| Negocio | `user-service`, `auth-service`, `security-service`, `category-service`, `book-service`, `copy-service`, `loan-service`, `reservation-service`, `fine-service`, `notification-service` |

## Tecnologías centralizadas

- Java 21.
- Spring Boot 3.5.14.
- Spring Cloud 2025.0.2.
- Springdoc OpenAPI 2.8.17.
- Maven Multi-Módulo.
- MySQL y Spring Data JPA únicamente en servicios persistentes.
- OpenFeign únicamente en servicios que ya realizaban comunicación remota.
- JUnit Jupiter y Mockito mediante `spring-boot-starter-test`.

## Dependencias comunes

Los servicios de negocio conservan `spring-boot-starter-web`, validación, Eureka Client y pruebas según su implementación original. JPA, MySQL, Feign, Security y JWT no se trasladaron como dependencias globales: permanecen declaradas únicamente en los módulos que las necesitan.

## Resultado del análisis

- Las versiones de Java, Spring Boot y Spring Cloud eran uniformes, por lo que pudieron centralizarse sin actualizar la plataforma existente.
- Se eliminaron de los hijos las propiedades y los BOM de Spring Cloud repetidos; ahora se heredan del padre.
- No se eliminó ninguna dependencia original.
- Se añadió Springdoc a los diez microservicios de negocio.
- Se mantuvieron los plugins particulares ya existentes en cada módulo.
- Se normalizó el BOM UTF-8 de 22 archivos Java y `.gitignore`; solo cambió la codificación inicial, no el texto fuente.

## Dependencias por módulo

### eureka-server

- `org.springframework.cloud:spring-cloud-starter-netflix-eureka-server`
- `org.springframework.boot:spring-boot-starter-test (test)`

### api-gateway

- `org.springframework.cloud:spring-cloud-starter-gateway-server-webflux`
- `org.springframework.cloud:spring-cloud-starter-netflix-eureka-client`
- `org.springframework.boot:spring-boot-starter-test (test)`

### user-service

- `org.springframework.boot:spring-boot-starter-data-jpa`
- `org.springframework.boot:spring-boot-starter-validation`
- `org.springframework.boot:spring-boot-starter-web`
- `org.springframework.cloud:spring-cloud-starter-netflix-eureka-client`
- `com.mysql:mysql-connector-j (runtime)`
- `org.projectlombok:lombok`
- `org.springdoc:springdoc-openapi-starter-webmvc-ui`
- `org.springframework.boot:spring-boot-starter-test (test)`

### auth-service

- `org.springframework.boot:spring-boot-starter-data-jpa`
- `org.springframework.boot:spring-boot-starter-security`
- `org.springframework.boot:spring-boot-starter-validation`
- `org.springframework.boot:spring-boot-starter-web`
- `org.springframework.cloud:spring-cloud-starter-netflix-eureka-client`
- `org.springframework.cloud:spring-cloud-starter-openfeign`
- `io.jsonwebtoken:jjwt-api`
- `io.jsonwebtoken:jjwt-impl (runtime)`
- `io.jsonwebtoken:jjwt-jackson (runtime)`
- `com.mysql:mysql-connector-j (runtime)`
- `org.projectlombok:lombok`
- `org.springdoc:springdoc-openapi-starter-webmvc-ui`
- `org.springframework.boot:spring-boot-starter-test (test)`
- `org.springframework.security:spring-security-test (test)`

### security-service

- `org.springframework.boot:spring-boot-starter-data-jpa`
- `org.springframework.boot:spring-boot-starter-validation`
- `org.springframework.boot:spring-boot-starter-web`
- `org.springframework.cloud:spring-cloud-starter-netflix-eureka-client`
- `org.springframework.cloud:spring-cloud-starter-openfeign`
- `com.mysql:mysql-connector-j (runtime)`
- `org.projectlombok:lombok`
- `org.springdoc:springdoc-openapi-starter-webmvc-ui`
- `org.springframework.boot:spring-boot-starter-test (test)`

### category-service

- `org.springframework.boot:spring-boot-starter-web`
- `org.springframework.boot:spring-boot-starter-data-jpa`
- `org.springframework.boot:spring-boot-starter-validation`
- `org.springframework.cloud:spring-cloud-starter-netflix-eureka-client`
- `com.mysql:mysql-connector-j (runtime)`
- `org.projectlombok:lombok`
- `org.springdoc:springdoc-openapi-starter-webmvc-ui`
- `org.springframework.boot:spring-boot-starter-test (test)`

### book-service

- `org.springframework.boot:spring-boot-starter-data-jpa`
- `org.springframework.boot:spring-boot-starter-validation`
- `org.springframework.boot:spring-boot-starter-web`
- `org.springframework.cloud:spring-cloud-starter-netflix-eureka-client`
- `org.springframework.cloud:spring-cloud-starter-openfeign`
- `org.springframework.boot:spring-boot-devtools (runtime)`
- `com.mysql:mysql-connector-j (runtime)`
- `org.projectlombok:lombok`
- `org.springdoc:springdoc-openapi-starter-webmvc-ui`
- `org.springframework.boot:spring-boot-starter-test (test)`

### copy-service

- `org.springframework.boot:spring-boot-starter-web`
- `org.springframework.boot:spring-boot-starter-data-jpa`
- `org.springframework.boot:spring-boot-starter-validation`
- `org.springframework.cloud:spring-cloud-starter-netflix-eureka-client`
- `org.springframework.cloud:spring-cloud-starter-openfeign`
- `com.mysql:mysql-connector-j (runtime)`
- `org.projectlombok:lombok`
- `org.springdoc:springdoc-openapi-starter-webmvc-ui`
- `org.springframework.boot:spring-boot-starter-test (test)`

### loan-service

- `org.springframework.boot:spring-boot-starter-web`
- `org.springframework.boot:spring-boot-starter-data-jpa`
- `org.springframework.boot:spring-boot-starter-validation`
- `org.springframework.cloud:spring-cloud-starter-netflix-eureka-client`
- `org.springframework.cloud:spring-cloud-starter-openfeign`
- `org.apache.httpcomponents.client5:httpclient5`
- `com.mysql:mysql-connector-j (runtime)`
- `org.projectlombok:lombok`
- `org.springdoc:springdoc-openapi-starter-webmvc-ui`
- `org.springframework.boot:spring-boot-starter-test (test)`

### reservation-service

- `org.springframework.boot:spring-boot-starter-web`
- `org.springframework.boot:spring-boot-starter-data-jpa`
- `org.springframework.boot:spring-boot-starter-validation`
- `org.springframework.cloud:spring-cloud-starter-netflix-eureka-client`
- `org.springframework.cloud:spring-cloud-starter-openfeign`
- `com.mysql:mysql-connector-j (runtime)`
- `org.projectlombok:lombok`
- `org.springdoc:springdoc-openapi-starter-webmvc-ui`
- `org.springframework.boot:spring-boot-starter-test (test)`

### fine-service

- `org.springframework.boot:spring-boot-starter-web`
- `org.springframework.boot:spring-boot-starter-data-jpa`
- `org.springframework.boot:spring-boot-starter-validation`
- `org.springframework.cloud:spring-cloud-starter-netflix-eureka-client`
- `org.springframework.cloud:spring-cloud-starter-openfeign`
- `com.mysql:mysql-connector-j (runtime)`
- `org.projectlombok:lombok`
- `org.springdoc:springdoc-openapi-starter-webmvc-ui`
- `org.springframework.boot:spring-boot-starter-test (test)`

### notification-service

- `org.springframework.boot:spring-boot-starter-web`
- `org.springframework.boot:spring-boot-starter-validation`
- `org.springframework.boot:spring-boot-starter-data-jpa`
- `com.mysql:mysql-connector-j`
- `org.springframework.cloud:spring-cloud-starter-openfeign`
- `org.springframework.cloud:spring-cloud-starter-netflix-eureka-client`
- `org.projectlombok:lombok`
- `org.springdoc:springdoc-openapi-starter-webmvc-ui`
- `org.springframework.boot:spring-boot-starter-test (test)`

