FROM amazoncorretto:17-alpine-jdk
WORKDIR /app
EXPOSE 8050
ADD ./target/store-0.0.1-SNAPSHOT.jar msvc-store.jar

ENTRYPOINT ["java", "-jar", "msvc-store.jar"]
