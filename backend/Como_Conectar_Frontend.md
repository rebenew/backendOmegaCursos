# Cómo Conectar tu Frontend con el Backend

## ¿Qué frontend usas?

Elige tu opción y sigue los pasos:

---

## 🅰️ Angular

### Paso 1: Crear el servicio
Crea un archivo `course.service.ts` en tu carpeta `services`:

```typescript
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CourseService {
  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  // Obtener todos los cursos
  getCourses(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/courses`);
  }

  // Obtener un curso específico
  getCourseById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/courses/${id}`);
  }
}
```

### Paso 2: Usar en tu componente
En tu componente (ej: `course-list.component.ts`):

```typescript
import { Component, OnInit } from '@angular/core';
import { CourseService } from '../services/course.service';

@Component({
  selector: 'app-course-list',
  template: `
    <div *ngIf="loading">Cargando...</div>
    <div *ngIf="error" class="error">{{ error }}</div>
    <div *ngFor="let course of courses" class="course-card">
      <h3>{{ course.title }}</h3>
      <p>{{ course.description }}</p>
      <span class="price">{{ course.price | currency }}</span>
    </div>
  `
})
export class CourseListComponent implements OnInit {
  courses: any[] = [];
  loading = true;
  error: string | null = null;

  constructor(private courseService: CourseService) { }

  ngOnInit() {
    this.courseService.getCourses().subscribe({
      next: (courses) => {
        this.courses = courses;
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Error al cargar los cursos';
        this.loading = false;
        console.error('Error:', error);
      }
    });
  }
}
```

### Paso 3: Configurar en app.module.ts
Asegúrate de tener HttpClientModule:

```typescript
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  imports: [
    HttpClientModule,
    // ... otros imports
  ],
  // ... resto de la configuración
})
export class AppModule { }
```

---

## ⚛️ React

### Paso 1: Instalar axios (si no lo tienes)
```bash
npm install axios
```

### Paso 2: Crear el servicio
Crea un archivo `courseService.js`:

```javascript
import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080';

const courseService = {
  // Obtener todos los cursos
  getCourses: async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/courses`);
      return response.data;
    } catch (error) {
      console.error('Error al obtener cursos:', error);
      throw error;
    }
  },

  // Obtener un curso específico
  getCourseById: async (id) => {
    try {
      const response = await axios.get(`${API_BASE_URL}/courses/${id}`);
      return response.data;
    } catch (error) {
      if (error.response?.status === 404) {
        throw new Error('Curso no encontrado');
      }
      console.error('Error al obtener curso:', error);
      throw error;
    }
  }
};

export default courseService;
```

### Paso 3: Crear un hook personalizado
Crea un archivo `useCourses.js`:

```javascript
import { useState, useEffect } from 'react';
import courseService from './courseService';

export const useCourses = () => {
  const [courses, setCourses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchCourses = async () => {
      try {
        setLoading(true);
        const data = await courseService.getCourses();
        setCourses(data);
        setError(null);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchCourses();
  }, []);

  return { courses, loading, error };
};
```

### Paso 4: Usar en tu componente
```jsx
import React from 'react';
import { useCourses } from './useCourses';

const CourseList = () => {
  const { courses, loading, error } = useCourses();

  if (loading) return <div>Cargando cursos...</div>;
  if (error) return <div className="error">Error: {error}</div>;

  return (
    <div className="courses-container">
      {courses.map(course => (
        <div key={course.id} className="course-card">
          <h3>{course.title}</h3>
          <p>{course.description}</p>
          <span className="price">${course.price}</span>
          <div className="tags">
            {course.tags?.map(tag => (
              <span key={tag.id} className="tag">{tag.name}</span>
            ))}
          </div>
        </div>
      ))}
    </div>
  );
};

export default CourseList;
```

---

## 🟢 Vue.js

### Paso 1: Crear el servicio
Crea un archivo `courseService.js`:

```javascript
const API_BASE_URL = 'http://localhost:8080';

export const courseService = {
  // Obtener todos los cursos
  async getCourses() {
    try {
      const response = await fetch(`${API_BASE_URL}/courses`);
      if (!response.ok) {
        throw new Error('Error al obtener cursos');
      }
      return await response.json();
    } catch (error) {
      console.error('Error:', error);
      throw error;
    }
  },

  // Obtener un curso específico
  async getCourseById(id) {
    try {
      const response = await fetch(`${API_BASE_URL}/courses/${id}`);
      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message);
      }
      return await response.json();
    } catch (error) {
      console.error('Error:', error);
      throw error;
    }
  }
};
```

### Paso 2: Crear un composable
Crea un archivo `useCourses.js`:

```javascript
import { ref, onMounted } from 'vue';
import { courseService } from './courseService';

export function useCourses() {
  const courses = ref([]);
  const loading = ref(true);
  const error = ref(null);

  const fetchCourses = async () => {
    try {
      loading.value = true;
      error.value = null;
      courses.value = await courseService.getCourses();
    } catch (err) {
      error.value = err.message;
    } finally {
      loading.value = false;
    }
  };

  onMounted(fetchCourses);

  return { courses, loading, error, fetchCourses };
}
```

### Paso 3: Usar en tu componente
```vue
<template>
  <div class="courses-container">
    <div v-if="loading" class="loading">
      Cargando cursos...
    </div>
    
    <div v-else-if="error" class="error">
      Error: {{ error }}
    </div>
    
    <div v-else class="courses-grid">
      <div 
        v-for="course in courses" 
        :key="course.id" 
        class="course-card"
      >
        <h3>{{ course.title }}</h3>
        <p>{{ course.description }}</p>
        <span class="price">${{ course.price }}</span>
        <div class="tags">
          <span 
            v-for="tag in course.tags" 
            :key="tag.id" 
            class="tag"
          >
            {{ tag.name }}
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useCourses } from '../composables/useCourses';

export default {
  name: 'CourseList',
  setup() {
    const { courses, loading, error } = useCourses();
    return { courses, loading, error };
  }
};
</script>
```

---

## 🟡 JavaScript Puro (Vanilla)

### Paso 1: Crear el servicio
Crea un archivo `courseService.js`:

```javascript
class CourseService {
  constructor() {
    this.baseURL = 'http://localhost:8080';
  }

  // Obtener todos los cursos
  async getCourses() {
    try {
      const response = await fetch(`${this.baseURL}/courses`);
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      return await response.json();
    } catch (error) {
      console.error('Error al obtener cursos:', error);
      throw error;
    }
  }

  // Obtener un curso específico
  async getCourseById(id) {
    try {
      const response = await fetch(`${this.baseURL}/courses/${id}`);
      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message);
      }
      return await response.json();
    } catch (error) {
      console.error('Error al obtener curso:', error);
      throw error;
    }
  }
}

// Crear instancia global
const courseService = new CourseService();
```

### Paso 2: Usar en tu HTML
```html
<!DOCTYPE html>
<html>
<head>
    <title>Omega Cursos</title>
    <style>
        .course-card {
            border: 1px solid #ddd;
            padding: 15px;
            margin: 10px;
            border-radius: 8px;
        }
        .loading { text-align: center; color: #666; }
        .error { color: red; text-align: center; }
        .price { font-weight: bold; color: green; }
        .tag { background: #007bff; color: white; padding: 2px 8px; margin: 2px; border-radius: 12px; font-size: 12px; }
    </style>
</head>
<body>
    <h1>Omega Cursos</h1>
    <div id="courses-container">
        <div class="loading">Cargando cursos...</div>
    </div>

    <script src="courseService.js"></script>
    <script>
        async function loadCourses() {
            const container = document.getElementById('courses-container');
            
            try {
                const courses = await courseService.getCourses();
                
                if (courses.length === 0) {
                    container.innerHTML = '<p>No hay cursos disponibles</p>';
                    return;
                }

                const coursesHTML = courses.map(course => `
                    <div class="course-card">
                        <h3>${course.title}</h3>
                        <p>${course.description}</p>
                        <div class="price">$${course.price}</div>
                        <div class="tags">
                            ${course.tags?.map(tag => `<span class="tag">${tag.name}</span>`).join('') || ''}
                        </div>
                    </div>
                `).join('');

                container.innerHTML = coursesHTML;
            } catch (error) {
                container.innerHTML = `<div class="error">Error: ${error.message}</div>`;
            }
        }

        // Cargar cursos cuando la página se carga
        document.addEventListener('DOMContentLoaded', loadCourses);
    </script>
</body>
</html>
```

---

## 🔧 Configuración para cualquier frontend

### Variables de entorno

**Angular (environment.ts):**
```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080'
};
```

**React (.env):**
```env
REACT_APP_API_URL=http://localhost:8080
```

**Vue.js (.env):**
```env
VUE_APP_API_URL=http://localhost:8080
```

### Manejo de errores universal

```javascript
const handleApiError = (error) => {
  if (error.response) {
    // Error del servidor (4xx, 5xx)
    const errorData = error.response.data;
    console.error('Error del servidor:', errorData.message);
    alert(`Error: ${errorData.message}`);
  } else if (error.request) {
    // Error de red
    console.error('Error de conexión');
    alert('Error de conexión con el servidor');
  } else {
    // Otro error
    console.error('Error:', error.message);
    alert('Error inesperado');
  }
};
```

---

## 🧪 Cómo probar la conexión

### Test rápido en la consola del navegador:
```javascript
// Abre la consola del navegador (F12) y pega esto:
fetch('http://localhost:8080/courses')
  .then(response => response.json())
  .then(courses => {
    console.log('✅ Conexión exitosa!');
    console.log('Cursos encontrados:', courses.length);
    console.log('Primer curso:', courses[0]);
  })
  .catch(error => {
    console.error('❌ Error de conexión:', error);
  });
```

### Test con cURL:
```bash
# En tu terminal:
curl http://localhost:8080/courses
```

---

## ❓ Problemas comunes

### "No puedo conectarme"
1. **Verifica** que el backend esté corriendo (`./mvnw spring-boot:run`)
2. **Revisa** que la URL sea correcta (`http://localhost:8080`)
3. **Prueba** primero en el navegador: `http://localhost:8080/courses`

### "Error de CORS"
- El CORS ya está configurado en el backend
- Verifica que estés usando la URL correcta
- Asegúrate de que el backend esté corriendo

### "404 Not Found"
- Verifica que el ID del curso exista
- Revisa la URL del endpoint

---

## 🎯 Resumen rápido

1. **Elige tu frontend** (Angular, React, Vue, JavaScript)
2. **Copia el código** de ejemplo
3. **Ajusta la URL** si es necesario
4. **Prueba la conexión** con el test rápido
5. **¡Listo!** Tu frontend ya está conectado

¿Necesitas ayuda con algún paso específico? ¡Pregunta! 😊
