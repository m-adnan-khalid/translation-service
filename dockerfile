FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Copy the built JAR file (make sure the name matches your pom.xml finalName)
COPY target/translationservice.jar app.jar

# Set default environment variables for H2
ENV SPRING_DATASOURCE_URL=jdbc:h2:mem:translationdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
ENV SPRING_DATASOURCE_DRIVER_CLASS_NAME=org.h2.Driver
ENV SPRING_DATASOURCE_USERNAME=sa
ENV SPRING_DATASOURCE_PASSWORD=
ENV SPRING_H2_CONSOLE_ENABLED=true
ENV SPRING_H2_CONSOLE_PATH=/h2-console
ENV JWT_SECRET=a3f8d7e2c5b1a9f6e4d2c7b0a5e8f3d1a6c9b2e7f4d8a1c5b3e9f6a2d7c4b0
ENV JWT_EXPIRATION=2700000

# Run with production profile and memory limits
ENTRYPOINT ["java","-jar","-Xms256m","-Xmx512m","app.jar","--spring.profiles.active=prod"]