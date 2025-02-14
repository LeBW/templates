FROM openjdk:8u212-jdk-alpine as builder

RUN apk --no-cache add curl \
    && mkdir -p /opt/ && cd /opt/ \
    && curl -sSfL https://mirrors.tuna.tsinghua.edu.cn/apache/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz -o apache-maven-3.6.3-bin.tar.gz \
    && tar xzvf apache-maven-3.6.3-bin.tar.gz \
    && rm -rf apache-maven-3.6.3-bin.tar.gz
COPY ./settings.xml /opt/apache-maven-3.6.3/conf/settings.xml

ENV MAVEN_HOME=/opt/apache-maven-3.6.3
ENV PATH=$PATH:$MAVEN_HOME/bin

WORKDIR /home/app

COPY . /home/app

RUN cd ./function && mvn clean package

FROM openfaas/of-watchdog:0.7.6 as watchdog

FROM registry.cn-shanghai.aliyuncs.com/libw/java8-with-skywalking-agent as ship

ARG service_name=Your_Application
ARG collector_url=skywalking-oap.skywalking:11800

COPY --from=watchdog /fwatchdog /usr/bin/fwatchdog
RUN chmod +x /usr/bin/fwatchdog

WORKDIR /home/app

COPY --from=builder /home/app/function/target/java-tmp-0.0.1-SNAPSHOT.jar ./java-tmp-0.0.1-SNAPSHOT.jar

ENV upstream_url="http://127.0.0.1:8082"
ENV mode="http"

ENV SW_AGENT_COLLECTOR_BACKEND_SERVICES=skywalking-oap.skywalking:11800

ENV fprocess="java -javaagent:/skywalking-agent/skywalking-agent.jar -Dskywalking.agent.service_name=${service_name} -Dskywalking.collector.backend_service=${collector_url} -jar -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap java-tmp-0.0.1-SNAPSHOT.jar"


EXPOSE 8080

HEALTHCHECK --interval=5s CMD [ -e /tmp/.lock ] || exit 1

CMD ["fwatchdog"]