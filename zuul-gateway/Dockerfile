FROM java:8
EXPOSE 8079
ADD target/zuul-gateway-0.0.1-SNAPSHOT.jar zuul-gateway.jar
ENTRYPOINT ["java", "-jar", "zuul-gateway.jar"]