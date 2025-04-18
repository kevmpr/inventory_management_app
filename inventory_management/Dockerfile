FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/inventory_management-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app_inventory.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app_inventory.jar"]