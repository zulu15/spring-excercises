FROM openjdk:17.0.2

WORKDIR /app

COPY ./target/microservicio-cursos-0.0.1-SNAPSHOT.jar .

EXPOSE 8002

#CMD sleep 20 && java -jar microservicio-cursos-0.0.1-SNAPSHOT.jar
ENTRYPOINT [ "java", "-jar", "microservicio-cursos-0.0.1-SNAPSHOT.jar" ]