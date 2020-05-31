FROM openjdk:11

ARG JAR_FILE
ADD target/${JAR_FILE} authentication.jar

CMD java \
    -Dspring.profiles.active=${SPRING_PROFILE} \
    -Dserver.port=${SERVER_PORT} \
    -jar \
    authentication.jar