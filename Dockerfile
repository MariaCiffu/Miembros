# Build del proyecto (Multi-Stage)
# --------------------------------
# Usamos una imagen de Maven para hacer build de proyecto con Java 17
# Llamaremos a este sub-entorno "build"
# Copiamos todo el contenido del repositorio
# Ejecutamos el comando mvn clean package (Generara un archivo JAR para el despliegue)
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package

# Usamos una imagen de Openjdk v17
# Exponemos el puerto que nuestro componente va a usar para escuchar peticiones
# Copiamos desde "build" el JAR generado (la ruta de generacion es la misma que veriamos en local) y lo movemos y renombramos en destino como
# Marcamos el punto de arranque de la imagen con el comando "java -jar app.jar" que ejecutará nuestro componente.
FROM openjdk:17
EXPOSE 8088

# Declarar las variables de entorno necesarias durante la construcción
ARG DB_URL
ARG DB_USER
ARG DB_PASSWORD

# Usar las variables durante la construcción
RUN echo "Database URL: $DB_URL"
RUN echo "Database User: $DB_USER"
RUN echo "Database Password: $DB_PASSWORD"

# Declarar las variables de entorno para el contenedor en ejecución
ENV DB_URL=${DB_URL}
ENV DB_USER=${DB_USER}
ENV DB_PASSWORD=${DB_PASSWORD}

COPY --from=build /target/ms-miembros-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
