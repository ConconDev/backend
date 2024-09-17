FROM ubuntu:latest

RUN apt-get update && \
    apt-get install -y openjdk-21-jre-headless && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

LABEL authors="sujinoh"

WORKDIR /app

# CI에서 빌드된 JAR 파일을 복사 (이 단계는 GitHub Actions에서 수행)
COPY build/libs/concon-0.0.1-SNAPSHOT.jar app.jar

# 환경 변수 설정
ENV JAVA_OPTS=""

# 애플리케이션 실행
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]