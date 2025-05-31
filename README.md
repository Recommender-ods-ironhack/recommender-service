# Recommender Service

## Descripción

El microservicio `recommender-service` ofrece funcionalidades de recomendación de artículos de vestimenta, combinando información de usuarios y del catálogo disponible a través de llamadas a otros microservicios (`user-service` y `clothing-item-service`) mediante Feign. Este servicio no persiste datos, sino que actúa como intermediario lógico para agregar y filtrar información de otros servicios, todo dentro de una arquitectura basada en microservicios registrada en Eureka y accedida por un API Gateway.

## Características

- **Agregación de Datos:** Consulta y combina datos desde otros servicios usando Feign Clients.
- **Recomendaciones Dinámicas:** Generación de recomendaciones basadas en filtros de usuario, artículos, descuentos, exceso de stock, etc.
- **Respuestas estandarizadas:** Manejo de errores y respuestas a través de `ResponseEntity` para un control preciso del estado HTTP.
- **Integración con Microservicios:** Registro en Eureka para descubrimiento de servicios y exposición a través de un API Gateway.

## Tecnologías Utilizadas

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Maven](https://maven.apache.org/)
- [Feign](https://spring.io/projects/spring-cloud-openfeign)
- [Lombok](https://projectlombok.org/)
- [Eureka](https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-eureka-server.html)

## Endpoints

> **Nota:** Todas las respuestas se devuelven encapsuladas en `ResponseEntity`.

### GET /api/item/{id}
- **Función:** Devuelve un artículo de vestimenta específico por su ID desde el `clothing-item-service`.
- **Response:** `ResponseEntity<ClothingItemDTO>`

### GET /api/user/{id}
- **Función:** Devuelve un usuario específico por su ID desde el `user-service`.
- **Response:** `ResponseEntity<UserDTO>`

### GET /api/items/excess-stock
- **Función:** Obtiene artículos con exceso de stock desde el `clothing-item-service`.
- **Response:** `ResponseEntity<List<ClothingItemDTO>>`

### GET /api/items/filtered
- **Función:** Filtra artículos por nombre, talla, estilos, color y precio máximo.
    - Parámetros opcionales:
        - `name` (String)
        - `size` (ESize)
        - `styles` (List<EStyle>)
        - `color` (String)
        - `maxPrice` (Integer)
- **Response:** `ResponseEntity<List<ClothingItemDTO>>`

### GET /api/items/discount
- **Función:** Devuelve los artículos que tienen descuento.
- **Response:** `ResponseEntity<List<ClothingItemDTO>>`

### GET /api/items/filtered/{id}
- **Función:** Genera recomendaciones personalizadas para el usuario especificado, con filtros opcionales:
    - `name` (String)
    - `color` (String)
    - `maxPrice` (Integer)
- **Response:** `ResponseEntity<RecommendationDTO>`

## Configuración y Ejecución

### Requisitos Previos

- **Instancias activas:** `clothing-item-service`, `user-service` y `Eureka` deben estar en ejecución para que este microservicio funcione correctamente.

### Archivo de Configuración

Dentro de la carpeta `src/main/resources`, crea el archivo `application.properties` con la siguiente configuración:

```properties
# Nombre de la aplicación para el registro en Eureka
spring.application.name=recommender-service

# Puerto del servidor
server.port=8083

# Configuración de Eureka para descubrimiento de servicios
eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka/

```

## Descripción de las propiedades

- **spring.application.name:** Define el nombre del microservicio para su registro en Eureka.
- **server.port:** Puerto en el que se ejecutará este servicio.
- **eureka.client.serviceUrl.defaultZone:** Proporciona la URL del servidor Eureka para habilitar el descubrimiento del servicio.

## Ejecución del Microservicio

1. **Verifica que Eureka esté corriendo.**
2. **Asegúrate de que los servicios `clothing-item-service` y `user-service` estén activos.**
3. **Ejecuta `RecommenderServiceApplication.java`** desde tu IDE o mediante la línea de comandos con:

```bash
mvn spring-boot:run

