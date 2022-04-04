FROM openjdk:17
ADD target/DockerCommentService.jar DockerCommentService.jar
EXPOSE 3015
ENTRYPOINT ["java","-jar","DockerCommentService.jar"]