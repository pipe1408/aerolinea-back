FROM openjdk:22-jdk
WORKDIR /app
COPY build/libs/aerolinea-back-0.0.1-SNAPSHOT.jar /app/app.jar
COPY data/db.mv.db /app/data/db.mv.db
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
