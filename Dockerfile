FROM ubuntu:18.04

RUN apt-get update && \
	apt install -y openjdk-8-jdk && \
	apt-get clean

ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/bin/
RUN export JAVA_HOME

RUN java -version

ENV APPLICATION_FOLDER=/app
RUN mkdir ${APPLICATION_FOLDER}
WORKDIR ${APPLICATION_FOLDER}

COPY target/numbers-converter-1.0-SNAPSHOT-spring-boot.jar /app/converter.jar
COPY entrypoint.sh /entrypoint.sh

CMD ["/entrypoint.sh"]
