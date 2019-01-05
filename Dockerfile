FROM openjdk:8-jre-alpine
RUN mkdir /app
WORKDIR /app
ADD ./sharycar-payments-api/target/sharycar-payments-api-1.0-SNAPSHOT.jar /app
EXPOSE 8082
CMD ["java", "-jar", "sharycar-payments-api-1.0-SNAPSHOT.jar"]
