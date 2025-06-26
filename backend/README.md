# 🚀 Omega Cursos - Backend API

## 📋 **Descripción**
Backend Spring Boot para gestión de cursos con endpoints REST para crear, leer, actualizar y eliminar cursos.

## ⚙️ **Requisitos Previos**
- Java 17 o superior
- Maven (incluido en el proyecto)
- Base de datos MySQL configurada

## 🚀 **Instalación y Ejecución**

### 1. **Configurar Java**
```bash
# Verificar versión de Java
java -version

# Configurar JAVA_HOME (Windows)
set JAVA_HOME=C:\Program Files\Java\jdk-17

# Configurar JAVA_HOME (Linux/Mac)
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
```

### 2. **Configurar Base de Datos**
Editar `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/tu_base_de_datos
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

### 3. **Ejecutar el Backend**
```bash
# Navegar al directorio del backend
cd backend

# Compilar y ejecutar
./mvnw spring-boot:run
```

**El servidor estará disponible en:** `http://localhost:8080`

## 🧪 **Probar la API**

### **Opción 1: Postman (Recomendado)**
1. Importar `Omega_Cursos_API.postman_collection.json`
2. Configurar variables de entorno:
   - `baseUrl`: `http://localhost:8080`
   - `courseId`: `1`
3. Probar endpoints

### **Opción 2: Navegador Web**
- GET `http://localhost:8080/courses` - Ver todos los cursos
- GET `http://localhost:8080/courses/1` - Ver curso específico

### **Opción 3: cURL**
```bash
# Obtener todos los cursos
curl http://localhost:8080/courses

# Crear nuevo curso
curl -X POST http://localhost:8080/courses \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Curso de Java",
    "modality": "VIRTUAL",
    "certification": "Certificado Java",
    "duration": "40 horas",
    "description": "Aprende Java desde cero",
    "price": 299.99
  }'
```

## 📚 **Endpoints Disponibles**

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/courses` | Obtener todos los cursos |
| GET | `/courses/{id}` | Obtener curso por ID |
| POST | `/courses` | Crear nuevo curso |
| PUT | `/courses/{id}` | Actualizar curso |
| DELETE | `/courses/{id}` | Eliminar curso |

## 🔧 **Estructura del Proyecto**
```
backend/
├── src/main/java/com/cursos/backend/
│   ├── controller/     # Controladores REST
│   ├── service/        # Lógica de negocio
│   ├── repository/     # Acceso a datos
│   └── model/          # Entidades JPA
├── src/main/resources/
│   └── application.properties
└── pom.xml
```

## ⚠️ **IMPORTANTE: Frontend**

### **Si necesitas el frontend:**

1. **Descargar/Clonar el frontend** desde su repositorio
2. **Configurar la conexión** al backend:
   - URL del backend: `http://localhost:8080`
   - Verificar CORS está habilitado
3. **Instalar dependencias** del frontend
4. **Ejecutar el frontend** en su puerto correspondiente

### **Frontend típico:**
```bash
# Ejemplo para Angular
cd frontend
npm install
ng serve

# Ejemplo para React
cd frontend
npm install
npm start

# Ejemplo para Vue
cd frontend
npm install
npm run dev
```

### **Verificar conexión:**
- Backend: `http://localhost:8080`
- Frontend: `http://localhost:3000` (React) o `http://localhost:4200` (Angular)

## 🐛 **Solución de Problemas**

### **Error: JAVA_HOME no configurado**
```bash
# Windows
set JAVA_HOME=C:\Program Files\Java\jdk-17

# Linux/Mac
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
```

### **Error: Puerto 8080 ocupado**
```bash
# Cambiar puerto en application.properties
server.port=8081
```

### **Error: Base de datos no conecta**
- Verificar MySQL esté ejecutándose
- Verificar credenciales en `application.properties`
- Verificar base de datos existe

## 📞 **Soporte**
Si tienes problemas:
1. Verificar logs del servidor
2. Confirmar Java y Maven instalados
3. Verificar configuración de base de datos
4. Probar endpoints con Postman

---

**¡El backend está listo para usar! 🎉** 