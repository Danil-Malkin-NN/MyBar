FROM maven AS build
COPY . .
RUN mvn package
RUN ls ./target


FROM openjdk:17-jdk-alpine
RUN mkdir /app
COPY --from=build target/my_bar-?.?.?-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java","-jar","app/app.jar"]