# Use an official OpenJDK runtime as a parent image
#FROM openjdk:20-jdk-slim
FROM eclipse-temurin:20-jdk-jammy
ENV DEBIAN_FRONTEND=noninteractive
WORKDIR /app

RUN apt-get update && \
    apt-get install -y --no-install-recommends curl ca-certificates && \
    rm -rf /var/lib/apt/lists/*

COPY build/libs/collabchat-0.0.1-SNAPSHOT.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]

#    When browser hits 8080, request comes to 8080 port of my PC.
#    Spring is saying serve on 8080 port of container/machine/PC u are running this spring app on
#    Dcoker file in collabchat is creating container and running spring app on 8080 port of that container.
#    But my PC's 8080 port is not mapped to container's 8080 port yet.
#    So to map my PC's 8080 port to container's 8080 port, I need to run below command in terminal:
#    docker run -p 8080:8080 <image_name> - Here first 8080 is my PC's port and second 8080 is container's port
#
#    So when I hit localhost:8080 in browser,
#    request will come to my PC's 8080 port which is mapped to container's 8080 port
#    and then spring app will serve.
