FROM zeryts/java:8-jre


ENV TZ=Asia/Shanghai
ENV JAVA_OPTS="-Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /eshop-register

WORKDIR /eshop-register

EXPOSE 8848

ADD ./target/eshop-register.jar ./

CMD sleep 30;java $JAVA_OPTS -jar eshop-register.jar


