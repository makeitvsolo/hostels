FROM gradle:8.3.0-jdk17 as build
ENV APP_HOME=/usr/dev/app
RUN mkdir -p $APP_HOME/src/main/java
WORKDIR $APP_HOME
COPY build.gradle gradlew gradlew.bat $APP_HOME
COPY gradle $APP_HOME/gradle
RUN ./gradlew build -x :bootJar -x test --continue
COPY . .
RUN ./gradlew build

FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /usr/dev/app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
