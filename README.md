# Huerta Casera API

API REST desarrollada con Java y Spring Boot para gestionar los cultivos de una huerta casera.

El proyecto fue creado con fines académicos para aplicar conceptos relacionados con el desarrollo de servicios REST, métodos HTTP, persistencia de datos, JPA, Hibernate, repositorios, operaciones CRUD, consultas personalizadas y manejo de respuestas HTTP.

La API permite registrar, consultar, actualizar, eliminar y buscar cultivos almacenados de forma persistente en una base de datos H2.

## Tecnologías utilizadas

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- H2 Database
- Maven
- Visual Studio Code
- Postman para probar las peticiones
- Git y GitHub para control de versiones

## Funcionalidades

La API permite:

- Registrar nuevos cultivos.
- Consultar todos los cultivos registrados.
- Consultar un cultivo por su identificador.
- Actualizar la información de un cultivo.
- Eliminar un cultivo.
- Buscar cultivos por tipo.
- Almacenar la información de forma persistente mediante H2.
- Responder con códigos HTTP de acuerdo con el resultado de cada operación.

## Estructura principal del proyecto

```text
src/main/java/com/alejocalvo/huerta_casera_api/

├── controller/
│   └── CultivoController.java
├── dto/
│   └── CultivoRequest.java
├── model/
│   └── Cultivo.java
├── repository/
│   └── CultivoRepository.java
└── HuertaCaseraApiApplication.java
```

- `CultivoController`: define los endpoints y atiende las peticiones HTTP.
- `Cultivo`: entidad que representa un cultivo y contiene los atributos `id`, `nombre`, `tipo` y `ubicacion`.
- `CultivoRequest`: DTO utilizado para recibir los datos enviados por el cliente al crear o actualizar un cultivo.
- `CultivoRepository`: repositorio que extiende `JpaRepository` y permite realizar las operaciones de persistencia y la búsqueda personalizada por tipo.
- `HuertaCaseraApiApplication`: clase principal que inicia la aplicación Spring Boot.

## Persistencia de datos

El proyecto utiliza Spring Data JPA y Hibernate para administrar la persistencia.

Como base de datos se utiliza H2 configurada en modo archivo:

```properties
spring.datasource.url=jdbc:h2:file:./data/huertadb
```

Esto permite conservar los registros incluso después de detener y volver a iniciar la aplicación.

La entidad `Cultivo` utiliza las anotaciones de JPA:

- `@Entity`
- `@Id`
- `@GeneratedValue`

El identificador se genera automáticamente al guardar cada nuevo cultivo.

## Requisitos previos

Antes de ejecutar el proyecto se necesita:

- Una versión de Java compatible con la configurada en `pom.xml`.
- Git, si se desea clonar el repositorio.

No es obligatorio instalar Maven globalmente porque el proyecto incluye Maven Wrapper.

Para comprobar Java:

```bash
java -version
javac -version
```

## Ejecución

1. Clonar el repositorio:

```bash
git clone <URL_DEL_REPOSITORIO>
```

2. Entrar en la carpeta:

```bash
cd huerta-casera-api
```

3. Iniciar la aplicación.

En Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

En Linux o macOS:

```bash
./mvnw spring-boot:run
```

La API quedará disponible en:

```text
http://localhost:8080
```

## Endpoints

| Método | Ruta | Descripción | Respuesta |
| --- | --- | --- | --- |
| `GET` | `/api/cultivos` | Obtiene todos los cultivos | `200 OK` |
| `GET` | `/api/cultivos/{id}` | Consulta un cultivo por ID | `200 OK` / `404 Not Found` |
| `GET` | `/api/cultivos/buscar?tipo=Hortaliza` | Busca cultivos por tipo | `200 OK` |
| `POST` | `/api/cultivos` | Registra un nuevo cultivo | `201 Created` |
| `PUT` | `/api/cultivos/{id}` | Actualiza un cultivo existente | `200 OK` / `404 Not Found` |
| `DELETE` | `/api/cultivos/{id}` | Elimina un cultivo | `204 No Content` / `404 Not Found` |

## Ejemplos de uso

### Crear un cultivo

```http
POST http://localhost:8080/api/cultivos
```

Cuerpo de la petición:

```json
{
  "nombre": "Tomate",
  "tipo": "Hortaliza",
  "ubicacion": "Patio"
}
```

Respuesta de ejemplo:

```json
{
  "id": 1,
  "nombre": "Tomate",
  "tipo": "Hortaliza",
  "ubicacion": "Patio"
}
```

Código de respuesta:

```text
201 Created
```

### Obtener todos los cultivos

```http
GET http://localhost:8080/api/cultivos
```

Respuesta de ejemplo:

```json
[
  {
    "id": 1,
    "nombre": "Tomate",
    "tipo": "Hortaliza",
    "ubicacion": "Patio"
  }
]
```

### Obtener un cultivo por ID

```http
GET http://localhost:8080/api/cultivos/1
```

Si el cultivo existe, responde:

```text
200 OK
```

Si el identificador no existe, responde:

```text
404 Not Found
```

### Actualizar un cultivo

```http
PUT http://localhost:8080/api/cultivos/1
```

Cuerpo de ejemplo:

```json
{
  "nombre": "Tomate Cherry",
  "tipo": "Hortaliza",
  "ubicacion": "Huerta trasera"
}
```

Si el cultivo existe, la información se actualiza y la API responde:

```text
200 OK
```

### Eliminar un cultivo

```http
DELETE http://localhost:8080/api/cultivos/1
```

Si el cultivo existe y es eliminado correctamente:

```text
204 No Content
```

Si el identificador no existe:

```text
404 Not Found
```

### Buscar cultivos por tipo

```http
GET http://localhost:8080/api/cultivos/buscar?tipo=Hortaliza
```

La consulta devuelve los cultivos cuyo tipo coincide con el valor enviado.

La búsqueda no distingue entre mayúsculas y minúsculas gracias al método personalizado definido en el repositorio:

```java
List<Cultivo> findByTipoIgnoreCase(String tipo);
```

## Operaciones CRUD

La API implementa las cuatro operaciones principales de gestión de información:

- **Create:** registrar cultivos mediante `POST`.
- **Read:** consultar cultivos mediante `GET`.
- **Update:** actualizar cultivos mediante `PUT`.
- **Delete:** eliminar cultivos mediante `DELETE`.

Las operaciones se realizan sobre la base de datos utilizando `CultivoRepository`, que extiende `JpaRepository<Cultivo, Long>`.

## Conceptos aplicados

- `@RestController`: identifica el controlador REST.
- `@RequestMapping`: establece la ruta principal de la API.
- `@GetMapping`: atiende solicitudes GET.
- `@PostMapping`: atiende solicitudes POST.
- `@PutMapping`: atiende solicitudes PUT.
- `@DeleteMapping`: atiende solicitudes DELETE.
- `@PathVariable`: obtiene valores incluidos en la ruta.
- `@RequestParam`: obtiene parámetros enviados en la URL.
- `@RequestBody`: convierte el JSON recibido en un objeto Java.
- `@Entity`: identifica la clase que será almacenada mediante JPA.
- `@Id`: identifica la llave primaria.
- `@GeneratedValue`: permite generar automáticamente el identificador.
- `JpaRepository`: proporciona las operaciones necesarias para consultar y modificar los registros.
- `ResponseEntity`: permite controlar los códigos HTTP enviados como respuesta.

## Verificación de persistencia

Para comprobar la persistencia se creó un cultivo mediante una petición `POST` y posteriormente se consultó mediante `GET`.

Después se detuvo completamente la aplicación y se volvió a iniciar. Al ejecutar nuevamente:

```http
GET http://localhost:8080/api/cultivos
```

los registros creados anteriormente continuaron almacenados y pudieron ser consultados, comprobando el funcionamiento de la persistencia mediante JPA, Hibernate y H2.

## Uso de inteligencia artificial

Durante el desarrollo de la actividad utilicé ChatGPT como herramienta de apoyo.

La inteligencia artificial fue utilizada principalmente para:

- Comprender el funcionamiento de JPA, Hibernate, H2 y `JpaRepository`.
- Orientar la implementación de las operaciones CRUD.
- Comprender la creación de consultas personalizadas mediante Spring Data JPA.
- Identificar y solucionar errores presentados durante el desarrollo.
- Orientar las pruebas realizadas mediante Postman.
- Apoyar la organización y actualización de la documentación del proyecto.

Las recomendaciones proporcionadas por la herramienta fueron revisadas, implementadas y probadas durante el desarrollo. El funcionamiento se verificó mediante la compilación y ejecución del proyecto, las respuestas HTTP obtenidas en Postman y la comprobación de que los registros permanecen almacenados después de reiniciar la aplicación.

## Autor

Juan Alejandro Calvo Aricapa