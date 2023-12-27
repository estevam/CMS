FROM openjdk:21
VOLUME /app
EXPOSE 8080
ARG JAR_FILE=target/est-blog.jar
ADD ${JAR_FILE} est-blog.jar
ENTRYPOINT ["java","-jar","/est-blog.jar"]