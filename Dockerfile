FROM openjdk:21

WORKDIR ./bang-backend
COPY ./backend/build .
CMD ["java","-jar","./libs/backend-0.0.1-SNAPSHOT.jar"]
