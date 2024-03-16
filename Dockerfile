FROM maven AS build
COPY . .
RUN mvn package
RUN ls ./target


FROM bellsoft/liberica-openjdk-alpine:21
RUN mkdir /app

# environment variable with default value
ENV SPRING_PROFILE=prod

COPY --from=build target/my_bar-*.*.*-*.jar /app/app.jar
ENTRYPOINT java -Dspring.profiles.active=$SPRING_PROFILE: -jar app/app.jar