# Huerta Casera API

API REST básica desarrollada con Java y Spring Boot para consultar y registrar cultivos de una huerta casera. El proyecto fue creado con fines académicos para practicar endpoints, métodos HTTP, parámetros, envío de datos en JSON, DTO y respuestas HTTP.

## Tecnologías utilizadas

- Java 21
- Spring Boot
- Spring Web
- Maven
- Visual Studio Code
- Postman para probar las peticiones

## Funcionalidades

La API permite:

- Consultar todos los cultivos disponibles.
- Consultar un cultivo por su identificador.
- Filtrar cultivos por tipo.
- Recibir los datos de un nuevo cultivo mediante JSON.
- Responder con códigos HTTP según el resultado de la consulta por ID.

## Estructura principal del proyecto

```text
src/main/java/com/alejocalvo/huerta_casera_api/
├── controller/
│   └── CultivoController.java
├── dto/
│   └── CultivoRequest.java
├── model/
│   └── Cultivo.java
└── HuertaCaseraApiApplication.java
```

- `CultivoController`: define las rutas y atiende las peticiones HTTP.
- `Cultivo`: representa un cultivo mediante los atributos `id`, `nombre` y `tipo`.
- `CultivoRequest`: DTO que representa los datos recibidos al crear un cultivo. Solo contiene `nombre` y `tipo`, porque el cliente no debe enviar el identificador.
- `HuertaCaseraApiApplication`: clase principal que inicia Spring Boot.

## Requisitos previos

Antes de ejecutar el proyecto se necesita:

- Java 21 o una versión compatible con la configurada en `pom.xml`.
- Git, si se desea clonar el repositorio.

No es obligatorio instalar Maven globalmente, porque el proyecto incluye Maven Wrapper.

Para comprobar Java:

```bash
java -version
javac -version
```

## Ejecución

1. Clonar el repositorio y entrar en la carpeta del proyecto:

```bash
git clone <URL_DEL_REPOSITORIO>
cd huerta-casera-api
```

2. Iniciar la aplicación.

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

| Método | Ruta | Descripción | Respuesta esperada |
| --- | --- | --- | --- |
| `GET` | `/api/cultivos` | Obtiene todos los cultivos | `200 OK` |
| `GET` | `/api/cultivos/{id}` | Busca un cultivo por ID mediante `@PathVariable` | `200 OK` o `404 Not Found` |
| `GET` | `/api/cultivos/buscar?tipo=Hortaliza` | Filtra cultivos por tipo mediante `@RequestParam` | `200 OK` |
| `POST` | `/api/cultivos` | Recibe un cultivo mediante JSON, `@RequestBody` y el DTO `CultivoRequest` | `200 OK` en la versión actual |

## Ejemplos de uso

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
    "tipo": "Hortaliza"
  },
  {
    "id": 2,
    "nombre": "Lechuga",
    "tipo": "Hortaliza"
  },
  {
    "id": 3,
    "nombre": "Cilantro",
    "tipo": "Hortaliza"
  },
  {
    "id": 4,
    "nombre": "Zanahoria",
    "tipo": "Hortaliza"
  }
]
```

### Obtener un cultivo por ID

```http
GET http://localhost:8080/api/cultivos/2
```

Si el cultivo existe, la API responde `200 OK`:

```json
{
  "id": 2,
  "nombre": "Lechuga",
  "tipo": "Hortaliza"
}
```

Si el ID no existe, por ejemplo `/api/cultivos/99`, responde `404 Not Found` sin cuerpo.

### Filtrar cultivos por tipo

```http
GET http://localhost:8080/api/cultivos/buscar?tipo=Hortaliza
```

El filtro no distingue entre mayúsculas y minúsculas porque utiliza `equalsIgnoreCase`.

### Crear un cultivo

En Postman se debe seleccionar:

- Método: `POST`.
- URL: `http://localhost:8080/api/cultivos`.
- Body: `raw`.
- Formato: `JSON`.
- Encabezado: `Content-Type: application/json`.

Cuerpo de la petición:

```json
{
  "nombre": "Pepino",
  "tipo": "Hortaliza"
}
```

Respuesta de la versión actual:

```json
{
  "id": 5,
  "nombre": "Pepino",
  "tipo": "Hortaliza"
}
```

## Conceptos aplicados

- `@RestController`: identifica la clase que atiende solicitudes REST.
- `@RequestMapping("/api/cultivos")`: define la ruta base del controlador.
- `@GetMapping`: relaciona métodos Java con solicitudes GET.
- `@PostMapping`: relaciona un método Java con solicitudes POST.
- `@PathVariable`: obtiene el ID incluido en la ruta.
- `@RequestParam`: obtiene el parámetro de consulta `tipo`.
- `@RequestBody`: convierte el JSON recibido en un objeto `CultivoRequest`.
- DTO: limita los datos de entrada a `nombre` y `tipo`.
- `ResponseEntity`: permite responder `200 OK` cuando un cultivo existe y `404 Not Found` cuando no existe.

## Alcance y limitaciones

Esta es una API académica sencilla y no utiliza base de datos. Los cultivos iniciales están definidos en memoria dentro de la aplicación.

El endpoint `POST` crea y devuelve un objeto con ID `5`, pero no lo agrega a una colección persistente. Por eso, después de registrar un cultivo, este no aparecerá en `GET /api/cultivos`, y los datos se pierden al finalizar la petición. Esta decisión mantiene el proyecto dentro del alcance básico de la actividad.

## Autor

Juan Alejandro Calvo Aricapa
