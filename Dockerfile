FROM maven AS build
COPY . .
RUN mvn package
RUN ls ./target


FROM bellsoft/liberica-openjdk-alpine:21
RUN mkdir /app
COPY --from=build target/my_bar-*.*.*-*.jar /app/app.jar
ENTRYPOINT ["java","-jar","app/app.jar"]