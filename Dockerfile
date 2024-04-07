FROM adoptopenjdk/openjdk17:alpine-jre
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} test-app.jar
ENTRYPOINT ["java","-jar","/test-app.jar"]