# Huerta Casera API

API REST desarrollada con Java y Spring Boot para gestionar los cultivos de una huerta casera.

El proyecto fue creado con fines académicos para aplicar conceptos relacionados con el desarrollo de servicios REST, métodos HTTP, persistencia de datos, JPA, Hibernate, relaciones entre entidades, consumo de servicios externos y observabilidad de una aplicación.

La API permite registrar, consultar, actualizar, eliminar y buscar cultivos almacenados de forma persistente en una base de datos MySQL. Los cultivos se encuentran asociados a una zona de la huerta y la aplicación también permite consultar información meteorológica mediante un servicio externo.

## Autor

**Juan Alejandro Calvo Aricapa**

## Tecnologías utilizadas

- Java 25
- Spring Boot 4.1.1
- Spring Web
- Spring Data JPA
- Hibernate
- MySQL
- Spring Boot Actuator
- Micrometer
- Prometheus
- RestClient
- Maven
- Visual Studio Code
- Postman
- Git y GitHub
- Open-Meteo API

## Funcionalidades

La API permite:

- Registrar nuevos cultivos.
- Consultar todos los cultivos registrados.
- Consultar un cultivo por su identificador.
- Actualizar la información de un cultivo.
- Eliminar un cultivo.
- Buscar cultivos por tipo.
- Registrar y consultar zonas de la huerta.
- Asociar cada cultivo con una zona.
- Almacenar la información de forma persistente en MySQL.
- Consultar información meteorológica mediante Open-Meteo.
- Manejar errores producidos al consultar el servicio externo.
- Consultar el estado de salud de la aplicación.
- Consultar métricas mediante Spring Boot Actuator.
- Registrar una métrica personalizada de cultivos creados.
- Exponer métricas en formato Prometheus.
- Registrar eventos mediante logs INFO, WARN y ERROR.

## Estructura principal del proyecto

```text
src/main/java/com/alejocalvo/huerta_casera_api/

├── controller/
│   ├── ClimaController.java
│   ├── CultivoController.java
│   └── ZonaController.java
├── dto/
│   ├── ClimaResponse.java
│   └── CultivoRequest.java
├── health/
│   └── HuertaHealthIndicator.java
├── model/
│   ├── Cultivo.java
│   └── Zona.java
├── repository/
│   ├── CultivoRepository.java
│   └── ZonaRepository.java
├── service/
│   └── ClimaService.java
└── HuertaCaseraApiApplication.java
```

### Componentes principales

- `CultivoController`: administra los endpoints relacionados con los cultivos y registra la métrica personalizada.
- `ZonaController`: administra las operaciones disponibles para las zonas de la huerta.
- `ClimaController`: expone el endpoint utilizado para consultar información meteorológica.
- `Cultivo`: entidad JPA que representa un cultivo.
- `Zona`: entidad JPA que representa una zona de la huerta.
- `CultivoRequest`: DTO utilizado para recibir los datos necesarios para crear o actualizar un cultivo.
- `ClimaResponse`: DTO utilizado para procesar la respuesta recibida desde Open-Meteo.
- `CultivoRepository`: repositorio JPA para las operaciones relacionadas con cultivos.
- `ZonaRepository`: repositorio JPA para las operaciones relacionadas con zonas.
- `ClimaService`: servicio encargado de realizar la comunicación con Open-Meteo mediante `RestClient`.
- `HuertaHealthIndicator`: indicador de salud personalizado de la aplicación.
- `HuertaCaseraApiApplication`: clase principal que inicia la aplicación Spring Boot.

## Base de datos MySQL

El proyecto utiliza MySQL como sistema de gestión de base de datos.

La conexión está configurada en `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/huerta_casera
spring.datasource.username=huerta_user
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

La contraseña no se almacena directamente en el repositorio. Se utiliza la variable de entorno:

```text
DB_PASSWORD
```

Antes de ejecutar la aplicación se debe:

1. Tener MySQL instalado y en ejecución.
2. Crear una base de datos llamada `huerta_casera`.
3. Crear o utilizar un usuario con permisos sobre esa base de datos.
4. Configurar la variable de entorno `DB_PASSWORD` con la contraseña correspondiente.

Ejemplo en Windows PowerShell:

```powershell
[System.Environment]::SetEnvironmentVariable("DB_PASSWORD", "SU_CONTRASEÑA", "User")
```

Por seguridad, la contraseña real no debe almacenarse en el código fuente ni publicarse en GitHub.

## Entidades y relación

El proyecto utiliza dos entidades principales:

### Zona

Representa una ubicación o sector de la huerta.

Sus principales atributos son:

- `id`
- `nombre`
- `descripcion`

### Cultivo

Representa una planta o cultivo registrado en la huerta.

Sus principales atributos son:

- `id`
- `nombre`
- `tipo`
- `zona`

### Relación entre entidades

Entre `Cultivo` y `Zona` se implementó una relación `ManyToOne`.

```java
@ManyToOne
@JoinColumn(name = "zona_id")
private Zona zona;
```

Esto significa que una zona puede estar asociada con varios cultivos, mientras que cada cultivo pertenece a una zona.

En MySQL, la relación se representa mediante la columna:

```text
zona_id
```

en la tabla `cultivo`, la cual referencia el identificador de la tabla `zona`.

## Requisitos previos

Para ejecutar el proyecto se necesita:

- Java 25 o una versión compatible con la configurada en `pom.xml`.
- MySQL.
- Git, si se desea clonar el repositorio.
- La variable de entorno `DB_PASSWORD` configurada.

No es obligatorio instalar Maven globalmente porque el proyecto incluye Maven Wrapper.

Para comprobar Java:

```bash
java -version
javac -version
```

## Ejecución

### 1. Clonar el repositorio

```bash
git clone https://github.com/AlejoCalvo/huerta-casera-api.git
```

### 2. Entrar en la carpeta

```bash
cd huerta-casera-api
```

### 3. Verificar MySQL y la variable de entorno

La base de datos `huerta_casera` debe existir y el usuario configurado debe tener permisos para acceder a ella.

### 4. Iniciar la aplicación

En Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

En Linux o macOS:

```bash
./mvnw spring-boot:run
```

La aplicación quedará disponible en:

```text
http://localhost:8080
```

## Endpoints de cultivos

| Método | Ruta | Descripción | Respuesta |
| --- | --- | --- | --- |
| `GET` | `/api/cultivos` | Obtiene todos los cultivos | `200 OK` |
| `GET` | `/api/cultivos/{id}` | Consulta un cultivo por ID | `200 OK` / `404 Not Found` |
| `GET` | `/api/cultivos/buscar?tipo=Hortaliza` | Busca cultivos por tipo | `200 OK` |
| `POST` | `/api/cultivos` | Registra un nuevo cultivo | `201 Created` / `400 Bad Request` |
| `PUT` | `/api/cultivos/{id}` | Actualiza un cultivo | `200 OK` / `400 Bad Request` / `404 Not Found` |
| `DELETE` | `/api/cultivos/{id}` | Elimina un cultivo | `204 No Content` / `404 Not Found` |

## Endpoints de zonas

| Método | Ruta | Descripción |
| --- | --- | --- |
| `GET` | `/api/zonas` | Obtiene las zonas registradas |
| `POST` | `/api/zonas` | Registra una nueva zona |

## Ejemplo: crear un cultivo

```http
POST http://localhost:8080/api/cultivos
```

Cuerpo:

```json
{
  "nombre": "Tomate",
  "tipo": "Hortaliza",
  "zonaId": 1
}
```

Respuesta de ejemplo:

```json
{
  "id": 1,
  "nombre": "Tomate",
  "tipo": "Hortaliza",
  "zona": {
    "id": 1,
    "nombre": "Huerta principal",
    "descripcion": "Zona principal para el cultivo de hortalizas y plantas aromáticas"
  }
}
```

## Búsqueda personalizada

Los cultivos pueden buscarse por tipo mediante:

```http
GET http://localhost:8080/api/cultivos/buscar?tipo=Hortaliza
```

La búsqueda no distingue entre mayúsculas y minúsculas gracias al método definido en `CultivoRepository`:

```java
List<Cultivo> findByTipoIgnoreCase(String tipo);
```

## Consumo de API externa

La aplicación consume la API pública de Open-Meteo para obtener información meteorológica relacionada con las condiciones de la huerta.

La comunicación se realiza mediante `RestClient`.

Endpoint de la aplicación:

```http
GET /api/clima?latitud={latitud}&longitud={longitud}
```

Ejemplo:

```http
GET http://localhost:8080/api/clima?latitud=5.30&longitud=-75.88
```

La aplicación procesa información como:

- Temperatura.
- Humedad relativa.
- Precipitación.
- Velocidad del viento.
- Hora de la medición.
- Zona horaria.

Ejemplo de respuesta:

```json
{
  "current": {
    "hora": "2026-09-26T17:15",
    "humedad": 99,
    "precipitacion": 0.5,
    "temperatura": 17.0,
    "velocidadViento": 8.0
  },
  "latitud": 5.307557,
  "longitud": -75.93051,
  "zonaHoraria": "America/Bogota"
}
```

### Manejo de errores del servicio externo

La consulta a Open-Meteo se encuentra protegida mediante manejo de excepciones.

Si el servicio externo no puede entregar una respuesta válida, la aplicación registra el error mediante un log de nivel `ERROR` y responde:

```text
502 Bad Gateway
```

con el mensaje:

```text
No fue posible obtener la información del clima.
```

Esto permite controlar el fallo del servicio externo sin provocar un error no controlado en la API.

## Observabilidad

La aplicación utiliza Spring Boot Actuator y Micrometer para proporcionar información sobre su estado y funcionamiento.

En `application.properties` se encuentran habilitados:

```properties
management.endpoints.web.exposure.include=health,metrics,prometheus
management.endpoint.health.show-details=always
```

### Estado de salud

```http
GET http://localhost:8080/actuator/health
```

El endpoint permite verificar el estado general de la aplicación, la conexión con MySQL y el indicador de salud personalizado.

El componente personalizado `huerta` informa:

- Estado de la API.
- Disponibilidad de la base de datos.
- Cantidad de cultivos registrados.

Ejemplo:

```json
{
  "status": "UP",
  "details": {
    "servicio": "Huerta Casera API",
    "baseDeDatos": "Disponible",
    "cultivosRegistrados": 6
  }
}
```

## Métricas

Las métricas disponibles pueden consultarse mediante:

```http
GET http://localhost:8080/actuator/metrics
```

### Métrica personalizada

Se implementó la métrica:

```text
cultivos.creados
```

Esta métrica utiliza un contador de Micrometer y aumenta cada vez que se registra correctamente un nuevo cultivo durante la ejecución de la aplicación.

Puede consultarse mediante:

```http
GET http://localhost:8080/actuator/metrics/cultivos.creados
```

El contador pertenece a la ejecución actual de la aplicación y vuelve a iniciar cuando la aplicación se reinicia.

## Prometheus

Las métricas también se exponen en formato compatible con Prometheus mediante:

```http
GET http://localhost:8080/actuator/prometheus
```

La métrica personalizada se representa como:

```text
cultivos_creados_total
```

No es necesario ejecutar un servidor Prometheus para consultar este endpoint.

## Logs

Se implementaron registros para eventos relevantes utilizando diferentes niveles:

- `INFO`: cuando un cultivo se crea correctamente.
- `WARN`: cuando se intenta crear un cultivo utilizando una zona inexistente.
- `ERROR`: cuando ocurre un error durante la consulta al servicio externo Open-Meteo.

Estos registros permiten conocer eventos importantes durante la ejecución y facilitan el diagnóstico de problemas.

## Operaciones CRUD

La API implementa las cuatro operaciones principales de gestión de información:

- **Create:** registrar cultivos mediante `POST`.
- **Read:** consultar cultivos mediante `GET`.
- **Update:** actualizar cultivos mediante `PUT`.
- **Delete:** eliminar cultivos mediante `DELETE`.

Las operaciones se realizan sobre MySQL utilizando Spring Data JPA, Hibernate y los repositorios correspondientes.

## Verificación de persistencia

La persistencia se verificó creando registros mediante peticiones HTTP y consultándolos posteriormente.

Después de detener completamente la aplicación y volverla a iniciar, los registros continuaron disponibles en MySQL, comprobando que la información permanece almacenada de forma persistente.

## Uso de inteligencia artificial

Durante el desarrollo de la actividad utilicé ChatGPT como herramienta de apoyo para comprender conceptos, orientar la implementación y solucionar errores.

La inteligencia artificial fue utilizada principalmente para:

- Orientar la migración de la persistencia hacia MySQL.
- Comprender e implementar la relación entre las entidades `Cultivo` y `Zona`.
- Orientar el uso de JPA e Hibernate.
- Comprender el consumo de servicios externos mediante `RestClient`.
- Apoyar el procesamiento de la respuesta JSON de Open-Meteo.
- Identificar y solucionar errores encontrados durante el desarrollo.
- Orientar la implementación de Spring Boot Actuator.
- Comprender la creación de métricas personalizadas con Micrometer.
- Implementar y verificar el indicador de salud personalizado.
- Configurar la exposición de métricas para Prometheus.
- Apoyar la organización y actualización de la documentación.

Las recomendaciones proporcionadas por la herramienta fueron revisadas y adaptadas antes de ser incorporadas al proyecto. Cada modificación fue probada mediante la ejecución de la aplicación, consultas HTTP, pruebas en Postman, revisión de logs y consultas a los endpoints de Actuator.

La inteligencia artificial se utilizó como herramienta de acompañamiento durante el aprendizaje y no como sustituto de la revisión, comprensión y prueba del código desarrollado.

## Repositorio

El código fuente del proyecto se encuentra disponible en GitHub:

```text
https://github.com/AlejoCalvo/huerta-casera-api
```