# Explicación del `pom.xml` padre

## Coordenadas y empaquetado

El padre utiliza `com.biblioteca:biblioteca-microservicios-parent:1.0.0-SNAPSHOT` y `packaging=pom`. Esto significa que no genera una aplicación ejecutable: coordina y configura los módulos hijos.

## Parent de Spring Boot

El proyecto padre hereda de `spring-boot-starter-parent` 3.5.14. Así se conservan las versiones administradas por Spring Boot que ya utilizaban todos los microservicios.

## Módulos

La sección `<modules>` registra los doce directorios. Maven los procesa como un solo reactor al ejecutar un comando desde la raíz.

## Properties

Se centralizaron Java 21, Spring Cloud 2025.0.2, Springdoc 2.8.17 y la codificación UTF-8. Los hijos ya no repiten estas propiedades.

## Dependency management

`dependencyManagement` administra versiones, pero no agrega dependencias automáticamente. Spring Cloud se importa como BOM y Springdoc tiene su versión definida una sola vez. Cada hijo sigue declarando las dependencias que realmente utiliza.

## Plugin management

Se administran `spring-boot-maven-plugin` y `maven-compiler-plugin`. Los módulos ejecutables conservan la declaración del plugin de Spring Boot y heredan la configuración común de Java 21 y UTF-8.

## Comandos

```bash
mvn clean install -DskipTests
```

En Windows, usando el wrapper agregado a la raíz:

```powershell
.\mvnw.cmd clean install -DskipTests
```
