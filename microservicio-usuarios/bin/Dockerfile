FROM openjdk:17.0.2

WORKDIR /app

COPY ./target/microservicio-usuarios-0.0.1-SNAPSHOT.jar .

ENV PORT=8001
EXPOSE ${PORT}

ENTRYPOINT [ "java", "-jar", "microservicio-usuarios-0.0.1-SNAPSHOT.jar" ]