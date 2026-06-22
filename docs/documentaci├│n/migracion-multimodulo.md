# Migración a Maven Multi-Módulo

## Objetivo

Unificar la administración Maven del proyecto sin alterar las reglas de negocio ni los endpoints existentes.

## Cambios realizados

1. Se creó un `pom.xml` padre con empaquetado `pom`.
2. Se registraron los doce módulos del proyecto.
3. Se centralizaron Java 21, Spring Boot 3.5.14, Spring Cloud 2025.0.2 y Springdoc 2.8.17.
4. Cada `pom.xml` hijo ahora hereda desde `../pom.xml`.
5. Las dependencias específicas continúan declaradas en su módulo original.
6. Se añadió Springdoc OpenAPI a los diez microservicios de negocio.
7. Se conservó `spring-boot-starter-test`, que incluye JUnit Jupiter y Mockito.
8. Se agregó Maven Wrapper en la raíz para ejecutar el reactor completo.
9. Se eliminó únicamente la marca BOM UTF-8 de 22 archivos Java; el contenido lógico no fue modificado.

## Validación recomendada

Desde la carpeta raíz:

```bash
mvn -version
mvn clean install -DskipTests
```

En Windows también puede utilizarse:

```powershell
.\mvnw.cmd clean install -DskipTests
```

No abrir módulos por separado en VSCode; abrir únicamente la carpeta raíz del proyecto padre.
