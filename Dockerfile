FROM openjdk:21
VOLUME /app
EXPOSE 8080
ARG JAR_FILE=target/cms.jar
ADD ${JAR_FILE} cms.jar
ENTRYPOINT ["java","-jar","/est-blog.jar"]