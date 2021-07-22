FROM maven:3.6.3-jdk-11-slim AS build
WORKDIR /app
COPY . /app/
RUN mvn clean package

FROM openjdk:latest
WORKDIR /app
COPY --from=build /app/target/ /app
CMD java -jar red-bull-demo-1.0-SNAPSHOT.jar
