FROM openjdk:17.0.2

WORKDIR /app

COPY ./target/msvc-gateway-0.0.1-SNAPSHOT.jar .

ENV PORT=8090
EXPOSE ${PORT}
ENTRYPOINT [ "java", "-jar", "msvc-gateway-0.0.1-SNAPSHOT.jar" ]