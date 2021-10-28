FROM openjdk:11 as build
RUN apk update && apk upgrade && apk add netcat-openbsd
RUN mkdir -p /usr/local/licensingservice
ADD licensing-service.build.libs.licensing-service-0.0.1-SNAPSHOT-plain.jar /usr/local/licensingservice/
ADD run.sh run.sh
RUN chmod +x run.sh
CMD ./run.sh