# Usa una imagen base con Maven y OpenJDK
FROM maven:3.8.4-openjdk-17-slim AS build

# Establece el directorio de trabajo
WORKDIR /inventory_management

# Copia el archivo pom.xml y el directorio src para compilar el proyecto
COPY pom.xml .
COPY src /inventory_management/src

# Ejecuta Maven para compilar el proyecto y generar el .jar
RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-slim

ARG JAR_FILE=target/inventory_management-0.0.1-SNAPSHOT.jar

COPY --from=build /inventory_management/target/inventory_management-0.0.1-SNAPSHOT.jar /app_inventory.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app_inventory.jar"]