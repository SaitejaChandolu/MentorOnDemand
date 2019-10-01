FROM java:8
EXPOSE 8085
ADD target/training-service-0.0.1-SNAPSHOT.jar training-service.jar
ENTRYPOINT ["java", "-jar", "training-service.jar"]