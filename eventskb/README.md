
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
   - Clonar el archivo `.example.env` y renombrarlo como `.env`. Ajustar los parametros de conexion a la base de datos postgres previamente creada
   - Actualizar las credenciales de la base de datos en `application.properties` o `application.yml`.

2. **Limpiar el proyecto (opcional pero recomendado)**:
   Este comando limpia cualquier archivo compilado o dependencias descargadas de una ejecución anterior.
   ```
   mvn clean
   ```
3. **Instalar las dependencias y compilar el proyecto :**
 Este comando descarga las dependencias necesarias, compila el código, y ejecuta las pruebas unitarias. También empaqueta la aplicación en un archivo `.jar`.
   ```
   mvn install
   ```
### **Ejecución del código**
1. **(Opción 1) Ejecutar la aplicación directamente sin empaquetar :**
- Ejecutar la aplicación sin empaquetarla en un archivo `.jar`
   ```
   mvn spring-boot:run
   ```

2. **(Opción 2) Empaquetar y luego ejecutar el archivo JAR :**
- Empaquetar la aplicación: Esto crea un archivo `eventsKB.jar` en la carpeta target
   ```
   mvn package
   ```
- Ejecutar el archivo JAR
   ```
   java -jar target/eventsKB.jar
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
