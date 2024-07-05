# Usar una imagen base de Java 22 con Maven
FROM openjdk:22-jdk-slim

# Instalación de Maven
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*


RUN apt-get update && \
    apt-get install -y curl && \
    curl -o /usr/local/bin/wait-for-it.sh https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh && \
    chmod +x /usr/local/bin/wait-for-it.sh


WORKDIR /app


COPY . .

 # Build the application
# RUN wait-for-it.sh db:5432 -- mvn clean install -DskipTests 

RUN wait-for-it.sh srv-captain--dbkb -- mvn clean install -Dspring.profiles.active=test
# -Dspring.profiles.active=test
#-DskipTests
# RUN /usr/local/bin/wait-for-it.sh db:5432 --strict --timeout=15 -- mvn clean install -Dspring.profiles.active=test
# RUN mvn clean package

# RUN java -jar target/eventsKB.jar
EXPOSE 8080

# Comando para ejecutar la aplicación
# ENTRYPOINT ["sh", "-c", "java", "-jar", "target/eventsKB.jar"]
# CMD ["java", "-jar", "target/eventsKB.jar"]
# ENTRYPOINT ["wait-for-it.sh", "db:5432", "--", "tail", "-f", "/dev/null"]
ENTRYPOINT ["wait-for-it.sh", "srv-captain--dbkb", "--", "java", "-jar", "target/eventsKB.jar"]

# ENTRYPOINT ["/usr/local/bin/wait-for-it.sh", "db:5432", "--strict", "--timeout=300", "--", "java", "-jar", "target/eventsKB.jar"]