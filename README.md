
# **Eventos CRUD Application**

## **Descripción del Proyecto**
Eventos CRUD Application es una aplicación web desarrollada para facilitar la creación, gestión y participación en eventos sociales, como cumpleaños, juntas, matrimonios y más. La aplicación permite a los usuarios registrarse, crear eventos, invitar a otros, y gestionar la asistencia y comentarios. 

Este proyecto utiliza **Spring Boot** para el backend, **[boostrap/htm/css/javascript]** para el frontend, y se implementa siguiendo principios de **DevOps** con herramientas como **Docker**, **Jenkins**, y **SonarQube**. Se desarrolla bajo metodologías ágiles utilizando **Scrum**.

## **Características Principales**
1. **Registro y Autenticación de Usuarios**: Los usuarios pueden crear cuentas, iniciar sesión y recuperar contraseñas.
2. **Gestión de Eventos**:
   - Crear, editar y eliminar eventos.
   - Visualizar eventos creados y a los que se está invitado.
3. **Invitaciones y Confirmaciones**:
   - Enviar invitaciones a participantes.
   - Confirmación de asistencia por parte de los invitados.
4. **Comunicación**: Comentarios y mensajes dentro de los eventos.
5. **Notificaciones**: Recordatorios y actualizaciones para eventos próximos.

## **Tecnologías Utilizadas**
- **Backend**: Spring Boot (Java)
- **Frontend**: Boostrap - html - css - javascript
- **Base de Datos**: PostgreSQL
- **Contenedorización**: Docker
- **CI/CD**: Jenkins
- **Análisis de Calidad de Código**: SonarQube

## **Requisitos Previos**
- **Java** JDK 22
- **Docker** y **Docker Compose**
- **PostgreSQL** (para base de datos)

## **Configuración del Proyecto**

### **Clonar el Repositorio**
```bash
git clone https://github.com/fidonoso/enventskb.git
cd enventskb
```

### **Backend**
1. **Configuración de la Base de Datos**:
   - Crear una base de datos en PostgreSQL.
   - Actualizar las credenciales de la base de datos en `application.properties` o `application.yml`.

2. **Construcción y Ejecución del Backend**:
   ```bash
   ./mvn clean install
   ./mvn spring-boot:run
   ```

### **Frontend**
1. **Instalación de Dependencias**:
   ```bash
   cd frontend

   ```

2. **Ejecución del Frontend**:
   ```bash

   ```

### **Contenedorización con Docker**
1. **Construir y Ejecutar los Contenedores**:
   ```bash
   docker-compose up --build
   ```

## **Contribuciones**
Las contribuciones son bienvenidas. Por favor, sigue los pasos a continuación para contribuir:
1. Descarga el repositorio.
2. Crea una nueva rama a partir de la rama dev(`git checkout -b nombre-rama`).
3. Realiza los cambios y confirma (`git commit -m 'Añadir nueva característica'`).
4. Haz push a la rama (`git push origin nombre-rama`).
5. Abre un Pull Request.

## **Licencia**


## **Contacto**
Para preguntas o sugerencias, por favor contacta a [grupo1@grupo1.com].
