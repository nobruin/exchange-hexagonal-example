FROM openjdk:17-jdk-slim-buster
WORKDIR /app
EXPOSE 9000
COPY assets/.env /app/assets/.env
ADD /build/libs/*-all.jar /app/app.jar

CMD ["java", "-jar", "/app/app.jar"]
