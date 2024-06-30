# Primera etapa: Construcción del proyecto
FROM maven:3.8.5-openjdk-17 AS build

# Declarar variables de entorno necesarias para la construcción
ARG DB_URL
ARG DB_USER
ARG DB_PASSWORD
ARG PROFILE
ARG HOSTNAME
ARG PORT

# Usar las variables durante la construcción
RUN echo "Database URL: $DB_URL"
RUN echo "Database User: $DB_USER"
RUN echo "Database Password: $DB_PASSWORD"
RUN echo "Profile use: $PROFILE"
RUN echo "Hostname: $HOSTNAME"
RUN echo "Port: $PORT"

COPY . .
RUN mvn clean package

# Segunda etapa: Ejecución de la aplicación
FROM openjdk:17
EXPOSE 8088

# Declarar variables de entorno necesarias para la ejecución
ENV DB_URL=${DB_URL}
ENV DB_USER=${DB_USER}
ENV DB_PASSWORD=${DB_PASSWORD}
ENV PROFILE=${PROFILE}
ENV HOSTNAME=${HOSTNAME}
ENV HOSTNAME=${PORT}

COPY --from=build /target/ms-miembros-0.0.1-SNAPSHOT.jar app.jar

# Establecer el perfil activo durante la ejecución
ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILE}", "-jar", "/app.jar"]
