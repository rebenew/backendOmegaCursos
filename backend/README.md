# 🚀 Omega Cursos - Backend API

## 📋 **Descripción**
Backend Spring Boot para gestión de cursos con endpoints REST para crear, leer, actualizar y eliminar cursos.

---

## 📝 **¿Qué hace este programa?**

Este backend permite gestionar cursos de manera sencilla a través de una API REST. Las principales funcionalidades relacionadas con cursos son:

- **Crear cursos:** Permite registrar nuevos cursos enviando los datos requeridos (título, modalidad, certificación, duración, descripción, precio y etiquetas) mediante una petición POST.
- **Ver cursos:** Puedes obtener la lista completa de cursos o consultar un curso específico por su ID. Además, puedes filtrar por título, modalidad o etiquetas.
- **Editar cursos:** Permite actualizar la información de un curso existente mediante una petición PUT, modificando cualquier campo del curso.
- **Eliminar cursos:** Puedes eliminar un curso de forma permanente usando una petición DELETE por ID.

### **Flujo de los endpoints principales**

| Método | Endpoint           | Descripción                                 |
|--------|--------------------|---------------------------------------------|
| GET    | `/courses`         | Listar todos los cursos (con filtros opc.)  |
| GET    | `/courses/{id}`    | Ver detalles de un curso por ID             |
| POST   | `/courses`         | Crear un nuevo curso                        |
| PUT    | `/courses/{id}`    | Editar un curso existente                   |
| DELETE | `/courses/{id}`    | Eliminar un curso                           |

- **Nota:** Algunos endpoints requieren autenticación de administrador (ver sección de autenticación más abajo).

#### **Detalles de cada operación**

- **Crear curso:**
  - Endpoint: `POST /courses`
  - Requiere autenticación (usuario administrador).
  - Ejemplo de cuerpo JSON:
    ```json
    {
      "title": "Curso de Java",
      "modality": "VIRTUAL",
      "certification": "Certificado Java",
      "duration": "40 horas",
      "description": "Aprende Java desde cero",
      "price": 299.99,
      "tags": [1, 2]
    }
    ```

- **Ver todos los cursos:**
  - Endpoint: `GET /courses`
  - Permite filtrar por título, modalidad o etiquetas usando parámetros de consulta.
  - Ejemplo: `/courses?title=Java&modality=VIRTUAL`

- **Ver curso por ID:**
  - Endpoint: `GET /courses/{id}`
  - Devuelve los detalles completos del curso.

- **Editar curso:**
  - Endpoint: `PUT /courses/{id}`
  - Requiere autenticación (usuario administrador).
  - El cuerpo es igual al de creación.

- **Eliminar curso:**
  - Endpoint: `DELETE /courses/{id}`
  - Requiere autenticación (usuario administrador).

---

## 🔐 **Autenticación y usuario administrador**

Este backend **no tiene endpoint de login**. La autenticación se realiza mediante HTTP Basic Auth usando un usuario definido en el archivo `SecurityConfig.java`:

- **Usuario:** `admin`
- **Contraseña:** `admin123`

Cuando un endpoint requiere autenticación, debes enviar estas credenciales en la cabecera `Authorization` usando el esquema Basic. Postman y cURL lo hacen automáticamente si configuras la autenticación básica.

---

## 🚦 **Guía rápida para iniciar la aplicación**

### 1. **Requisitos previos**
- Java 17 o superior
- MySQL en ejecución

### 2. **Configura la base de datos**
Edita `src/main/resources/application.properties` con tus credenciales y URL de MySQL:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/tu_base_de_datos
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

### 3. **Compila y ejecuta el backend**
```bash
cd backend
./mvnw spring-boot:run
```
El backend estará disponible en: `http://localhost:8080`

---

## 🧪 **Guía para probar los endpoints**

### **A) Usando Postman (Recomendado)**

1. **Abre Postman** e importa el archivo `Omega_Cursos_API.postman_collection.json` incluido en el proyecto.
2. **Configura la autenticación básica** en las peticiones que lo requieran:
   - Ve a la pestaña "Authorization" de la petición.
   - Selecciona el tipo "Basic Auth".
   - Usuario: `admin`
   - Contraseña: `admin123`
3. **Prueba los endpoints de cursos:**
   - **Obtener todos los cursos:** `GET /courses` (no requiere autenticación)
   - **Obtener curso por ID:** `GET /courses/{id}` (no requiere autenticación)
   - **Crear curso:** `POST /courses` (**requiere autenticación básica**)
   - **Editar curso:** `PUT /courses/{id}` (**requiere autenticación básica**)
   - **Eliminar curso:** `DELETE /courses/{id}` (**requiere autenticación básica**)
4. **Prueba los endpoints de etiquetas** si lo deseas (`/tags`).
5. **Verifica el manejo de errores** usando las peticiones de la sección "🧪 Pruebas de Error" de la colección.

### **B) Usando cURL**

- **Obtener todos los cursos:**
  ```bash
  curl http://localhost:8080/courses
  ```
- **Obtener curso por ID:**
  ```bash
  curl http://localhost:8080/courses/1
  ```
- **Crear un curso (requiere autenticación básica):**
  ```bash
  curl -X POST http://localhost:8080/courses \
    -u admin:admin123 \
    -H "Content-Type: application/json" \
    -d '{
      "title": "Curso de Java",
      "modality": "VIRTUAL",
      "certification": "Certificado Java",
      "duration": "40 horas",
      "description": "Aprende Java desde cero",
      "price": 299.99,
      "tags": [1,2]
    }'
  ```
- **Editar un curso (requiere autenticación básica):**
  ```bash
  curl -X PUT http://localhost:8080/courses/1 \
    -u admin:admin123 \
    -H "Content-Type: application/json" \
    -d '{
      "title": "Curso de Java Avanzado",
      "modality": "PRESENCIAL",
      "certification": "Certificado Avanzado",
      "duration": "60 horas",
      "description": "Java avanzado",
      "price": 399.99,
      "tags": [1,2]
    }'
  ```
- **Eliminar un curso (requiere autenticación básica):**
  ```bash
  curl -X DELETE http://localhost:8080/courses/1 \
    -u admin:admin123
  ```

### **C) Desde el navegador**

- Puedes acceder directamente a:
  - `http://localhost:8080/courses` para ver todos los cursos (GET)
  - `http://localhost:8080/courses/1` para ver un curso específico (GET)

---

## 🌐 **Conexión con el Frontend**

Este backend está diseñado para integrarse fácilmente con el frontend oficial del proyecto, desarrollado en Angular. Puedes encontrar el repositorio del frontend aquí:

➡️ [Frontend Omega Cursos (Angular)](https://github.com/rebenew/frontendOmegaCursos)

### **Pasos para conectar el frontend:**
1. Asegúrate de que el backend esté corriendo en `http://localhost:8080`.
2. Clona el repositorio del frontend:
   ```bash
   git clone https://github.com/rebenew/frontendOmegaCursos.git
   cd frontendOmegaCursos
   npm install
   npm start
   ```
3. Configura el archivo de entorno del frontend (`src/environments/environment.ts`) para que los endpoints apunten a `http://localhost:8080`.
4. Accede al frontend en `http://localhost:4200`.

El frontend consume los endpoints de cursos, autenticación y usuarios definidos en este backend. Si tienes problemas de CORS, asegúrate de que el backend permita peticiones desde el origen del frontend.

---

## 📚 **Resumen de Endpoints**

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/courses` | Obtener todos los cursos (con filtros) |
| GET | `/courses/{id}` | Obtener curso por ID |
| POST | `/courses` | Crear nuevo curso (admin) |
| PUT | `/courses/{id}` | Actualizar curso (admin) |
| DELETE | `/courses/{id}` | Eliminar curso (admin) |

---

## 🔧 **Estructura del Proyecto (MVC)**

La organización del backend sigue el patrón de diseño **MVC (Modelo-Vista-Controlador)**, que separa la lógica de negocio, la gestión de datos y la exposición de endpoints REST. A continuación se explica el propósito de cada carpeta principal:

```
backend/
└── src/main/java/com/cursos/backend/
    ├── controller/     # Controladores (Capa Controlador)
    ├── service/        # Servicios (Capa de Lógica de Negocio)
    ├── repository/     # Repositorios (Capa de Acceso a Datos)
    ├── model/          # Modelos/Entidades (Capa Modelo)
    ├── DTO/            # Objetos de Transferencia de Datos
    ├── converter/      # Conversores entre entidades y DTOs
    └── config/         # Configuración general y de seguridad
```

### **Explicación de cada carpeta:**

- **controller/**
  - Contiene las clases que exponen los endpoints REST. Reciben las peticiones HTTP, validan los datos de entrada y delegan la lógica al servicio correspondiente. Ejemplo: `CourseController.java`.

- **service/**
  - Implementa la lógica de negocio de la aplicación. Aquí se procesan las reglas, validaciones y operaciones principales antes de acceder a la base de datos. Ejemplo: `CourseService.java`.

- **repository/**
  - Define las interfaces para el acceso a la base de datos usando Spring Data JPA. Permite realizar operaciones CRUD sobre las entidades. Ejemplo: `CourseRepository.java`.

- **model/**
  - Contiene las entidades JPA que representan las tablas de la base de datos. Cada clase es un modelo de datos. Ejemplo: `Course.java`, `Tag.java`.

- **DTO/**
  - Define los Data Transfer Objects, que son objetos usados para transferir datos entre el backend y el frontend, evitando exponer directamente las entidades de la base de datos. Ejemplo: `CourseRequestDTO.java`, `CourseResponseDTO.java`.

- **converter/**
  - Incluye clases utilitarias para convertir entre entidades y DTOs, facilitando la separación entre la capa de datos y la de presentación.

- **config/**
  - Contiene la configuración general del proyecto, como la seguridad (Spring Security), CORS, etc. Ejemplo: `SecurityConfig.java`, `WebConfig.java`.

- **resources/** (en `src/main/resources/`)
  - Archivos de configuración y recursos estáticos, como `application.properties`.

Esta estructura facilita el mantenimiento, la escalabilidad y la separación de responsabilidades en el desarrollo del backend.