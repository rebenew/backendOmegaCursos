@echo off
echo ========================================
echo    OMEGA CURSOS - BACKEND TESTER
echo ========================================
echo.
echo Iniciando el backend Spring Boot...
echo.
echo Pasos para probar con Postman:
echo 1. Abre Postman
echo 2. Importa el archivo: Omega_Cursos_API.postman_collection.json
echo 3. Configura las variables de entorno
echo 4. Prueba los endpoints
echo.
echo URLs disponibles:
echo - GET http://localhost:8080/courses
echo - GET http://localhost:8080/courses/1
echo - POST http://localhost:8080/courses
echo - PUT http://localhost:8080/courses/1
echo - DELETE http://localhost:8080/courses/1
echo - GET http://localhost:8080/tags
echo - POST http://localhost:8080/tags
echo.
echo Presiona Ctrl+C para detener el servidor
echo ========================================
echo.

mvnw spring-boot:run

pause 