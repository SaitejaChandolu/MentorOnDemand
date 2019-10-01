FROM java:8
EXPOSE 8065
ADD target/search-service-0.0.1-SNAPSHOT.jar search-service.jar
ENTRYPOINT ["java", "-jar", "search-service.jar"]