FROM maven AS build
COPY . .
RUN mvn package -DskipTests=true
RUN ls ./target

FROM bellsoft/liberica-openjdk-alpine:21
RUN mkdir /app

# environment variable with default value
ENV SPRING_PROFILE=prod

COPY --from=build /target/my_bar-*.jar /app/app.jar

# Открываем порты: HTTP (8080) и debug (5005)
EXPOSE 8080
EXPOSE 5005

ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILE}", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "/app/app.jar"]
