./gradlew build
docker build --tag=order-book:latest .
docker-compose up -d