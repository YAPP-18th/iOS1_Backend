FROM openjdk:11-jre-slim

WORKDIR /root

COPY ./build/libs/*.jar .

CMD java -jar -Dspring.profiles.active=${active} *.jar