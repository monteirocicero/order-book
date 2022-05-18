FROM ghcr.io/graalvm/graalvm-ce:20.3.6
COPY build/libs/order-book-0.0.1-SNAPSHOT.jar order-book-0.0.1.jar
ENTRYPOINT ["java","-jar","/order-book-0.0.1.jar"]