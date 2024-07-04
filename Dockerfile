## Build stage
#FROM gradle:8.6.0-jdk21 AS BUILD
#WORKDIR /home/app
#
## Copy the Gradle build script and the main application file
#COPY ./build.gradle /home/app/build.gradle
#COPY ./src/main/java/com/icia/weatherhelper/WeatherHelperApplication.java /home/app/src/main/java/com/icia/weatherhelper/WeatherHelperApplication.java
#
## Perform an initial clean build
#RUN gradle clean build
#
## Copy the rest of the application source code
#COPY . /home/app
#
## Perform the final build
#RUN gradle clean build
#
## Runtime stage
#FROM openjdk:21
#EXPOSE 8080
#
## Copy the built JAR file from the build stage to the runtime stage
#COPY --from=BUILD /home/app/build/libs/*.jar app.jar
#
## Command to run the application
#ENTRYPOINT [ "sh", "-c", "java -jar /app.jar" ]