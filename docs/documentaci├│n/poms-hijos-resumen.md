# Resumen de los `pom.xml` hijos

Todos los hijos ahora apuntan al padre mediante:

```xml
<parent>
    <groupId>com.biblioteca</groupId>
    <artifactId>biblioteca-microservicios-parent</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <relativePath>../pom.xml</relativePath>
</parent>
```

Se retiraron de cada hijo las propiedades repetidas y el `dependencyManagement` repetido de Spring Cloud. Las dependencias funcionales originales se conservaron.

## eureka-server

Infraestructura de descubrimiento. Conserva Eureka Server y pruebas; no recibe Swagger porque no expone una API de negocio.

## api-gateway

Infraestructura de enrutamiento WebFlux. Conserva Gateway, Eureka Client y pruebas; no se mezcló el starter WebMVC de Swagger.

## user-service

Persistencia JPA/MySQL, validación, web y Eureka. Se añadió Swagger; JUnit y Mockito ya están incluidos en starter-test.

## auth-service

Mantiene JPA, Security, Feign, JWT, MySQL y spring-security-test. Se añadió Swagger sin modificar el flujo de login.

## security-service

Mantiene JPA, MySQL, Feign, roles y asignaciones. Se añadió Swagger.

## category-service

Mantiene CRUD JPA/MySQL y Eureka. Se añadió Swagger.

## book-service

Mantiene JPA/MySQL, Feign hacia Category/Security y DevTools. Se añadió Swagger; `X-User-Id` permanece igual.

## copy-service

Mantiene JPA/MySQL y Feign hacia Book. Se añadió Swagger y se normalizó la codificación BOM de archivos existentes.

## loan-service

Mantiene JPA/MySQL, Feign y HttpClient 5. Se añadió Swagger; `X-User-Id` permanece igual.

## reservation-service

Mantiene JPA/MySQL y Feign. Se añadió Swagger y se normalizó la codificación BOM de archivos existentes.

## fine-service

Mantiene JPA/MySQL y Feign para usuarios, préstamos y notificaciones. Se añadió Swagger.

## notification-service

Mantiene JPA/MySQL, Feign y validación. Se añadió Swagger.

