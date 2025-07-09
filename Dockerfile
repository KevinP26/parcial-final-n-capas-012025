FROM eclipse-temurin:21-jdk-alpine

WORKDIR /

COPY target/*.jar parcial_final.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "parcial_final.jar"]