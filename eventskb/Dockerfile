# Usar imagen base de Java 22 con Maven
FROM openjdk:22-jdk-slim

# Instalación de Maven y utilidades
RUN apt-get update && apt-get install -y maven curl && \
    curl -o /usr/local/bin/wait-for-it.sh https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh && \
    chmod +x /usr/local/bin/wait-for-it.sh && \
    rm -rf /var/lib/apt/lists/*

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el archivo pom.xml y descargar dependencias (para caché de capas)
COPY pom.xml ./
RUN mvn dependency:go-offline

# Copiar el código de la aplicación
COPY . .

# Construir la aplicación con Maven y perfil de prueba (esperando la base de datos)
RUN /usr/local/bin/wait-for-it.sh dbkb_postgres:5432 -- mvn clean install -Dspring.profiles.active=test

# Exponer el puerto de la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación, esperando a la base de datos PostgreSQL
# ENTRYPOINT ["wait-for-it.sh", "postgres:5432", "--", "java", "-jar", "target/eventsKB.jar"]
ENTRYPOINT ["sh", "-c", "/usr/local/bin/wait-for-it.sh dbkb_postgres:5432 -- /usr/local/bin/wait-for-it.sh elk_stack_logstash:5000 -- java -jar target/eventsKB.jar"]

# CONSTRUIR LA IMAGEN POR SEPARADO PARA DOCKER STACK
# docker image rmi eventskb-app 

# docker build -t eventskb-app:latest .