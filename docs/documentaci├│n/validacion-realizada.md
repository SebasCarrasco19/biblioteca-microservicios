# Validación realizada

## Comprobaciones completadas

- Los 12 `pom.xml` hijos y el `pom.xml` padre son XML válido.
- Los 12 módulos heredan de `biblioteca-microservicios-parent` mediante `../pom.xml`.
- El padre registra exactamente los 12 módulos existentes.
- Las dependencias originales de cada módulo fueron conservadas.
- Springdoc permanece en los 10 microservicios de negocio.
- `spring-boot-starter-test` permanece en todos los módulos.
- H2 fue agregado únicamente con alcance `test` en los 10 microservicios con persistencia.
- Existen configuraciones de prueba independientes en `src/test/resources/application.yml`.
- Se incorporaron 43 clases de prueba con 46 métodos `@Test`.
- Cada microservicio de negocio posee pruebas de servicio, controlador y repositorio.
- Los paquetes declarados en las pruebas coinciden con su estructura de carpetas.
- Los archivos Java nuevos fueron revisados sintácticamente.
- El contenido de `src/main` permanece idéntico a la versión corregida que ya compilaba.
- Los cuatro documentos Word originales fueron incorporados en `docs/documentación/etapas/`.
- No se incluyen carpetas `target` ni binarios compilados en la entrega.

## Validación Maven

La versión multi-módulo anterior fue compilada exitosamente en el computador de desarrollo con `BUILD SUCCESS` utilizando:

```powershell
.\mvnw.cmd clean install -DskipTests
```

Esta nueva etapa incorpora pruebas y debe validarse desde la carpeta raíz con:

```powershell
.\mvnw.cmd clean install
```

El entorno utilizado para preparar este ZIP dispone de Java 21, pero no puede descargar Maven ni las dependencias remotas. Por esa razón, la ejecución completa de las pruebas debe realizarse en el computador de desarrollo, que ya tiene el repositorio Maven descargado mediante el Wrapper.
