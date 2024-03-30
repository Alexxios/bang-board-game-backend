FROM openjdk:21

RUN export LD_BIND_NOW=1
ARG JAR_FILE
COPY ${JAR_FILE} backend.jar
ENTRYPOINT ["java","-jar","backend.jar"]
