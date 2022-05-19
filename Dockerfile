FROM openjdk:17.0.2-slim-buster
VOLUME /main-app
ADD build/libs/order-book-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]