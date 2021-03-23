FROM java:8
LABEL maintainer="th.park"
VOLUME /tmp
EXPOSE 8888

ARG JAR_FILE=target/test-0.0.1.jar
ADD ${JAR_FILE} test.jar
ENTRYPOINT [ "java" , "-jar" , "/test.jar" , "--spring.profiles.active=dev"]

