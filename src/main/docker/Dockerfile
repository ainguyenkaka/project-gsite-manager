FROM openjdk:8-jre-alpine

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS

ADD *.war /app.war

VOLUME /tmp
EXPOSE 8082
CMD java -Djava.security.egd=file:/dev/./urandom -jar /app.war
