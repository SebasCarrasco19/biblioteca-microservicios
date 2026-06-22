# Aplicación de las cuatro etapas Maven Multi-Módulo

Este documento registra cómo se aplicaron al proyecto las cuatro etapas entregadas para Desarrollo FullStack I.

## Etapa 1 — Migración a Maven Multi-Módulo

Estado: **aplicada**.

- Existe un `pom.xml` padre con `packaging` igual a `pom`.
- Los 12 componentes están registrados como módulos.
- Java, Spring Boot, Spring Cloud y Springdoc se administran centralmente.
- La carpeta raíz es el único proyecto que debe abrirse en VS Code.
- La compilación estructural inicial se realiza con:

```powershell
.\mvnw.cmd clean install -DskipTests
```

## Etapa 2 — Configuración de microservicios

Estado: **aplicada**.

- JPA y MySQL permanecen solamente en los servicios con persistencia.
- OpenFeign permanece solamente en los servicios que llaman a otros servicios.
- Eureka Client permanece en los servicios registrados.
- Swagger/OpenAPI está incorporado en los diez microservicios de negocio.
- Las rutas reales y la lógica existente no fueron modificadas.

## Etapa 3 — Ejecución del sistema

Estado: **documentada y preparada**.

Orden recomendado:

1. MySQL.
2. Eureka Server.
3. Microservicios de negocio.
4. API Gateway.

Ejemplo desde la carpeta padre:

```powershell
.\mvnw.cmd -pl eureka-server spring-boot:run
.\mvnw.cmd -pl user-service spring-boot:run
.\mvnw.cmd -pl api-gateway spring-boot:run
```

Cada comando debe ejecutarse en una terminal distinta cuando los servicios deban permanecer activos al mismo tiempo.

## Etapa 4 — Calidad y pruebas unitarias

Estado: **incorporada**.

Se agregaron pruebas de tres niveles:

- **Service tests:** JUnit 5 y Mockito para reglas de negocio aisladas.
- **Controller tests:** MockMvc con servicios simulados para comprobar rutas y códigos HTTP.
- **Repository tests:** `@DataJpaTest` con H2 en memoria para comprobar consultas JPA sin depender de MySQL local.
- **Context tests:** comprueban que el contexto de Spring Boot pueda iniciarse con configuración de prueba.

El proyecto contiene 43 clases de prueba y 46 métodos anotados con `@Test`.

La validación final indicada por la etapa 4 debe ejecutarse desde la raíz:

```powershell
.\mvnw.cmd clean install
```

Esta vez no se utiliza `-DskipTests`, porque Maven debe ejecutar las pruebas antes de generar los JAR.

## Archivos Word originales

Los cuatro documentos originales se conservaron en:

```text
docs/documentación/etapas/
```

Funcionan como respaldo académico y guía del proceso aplicado.
