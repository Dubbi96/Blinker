# 1. 베이스 이미지 설정 (JDK 17)
FROM openjdk:17-jdk-slim

# 2. 애플리케이션 디렉토리 설정
WORKDIR /app

# 필요한 패키지 설치
RUN apt-get update && apt-get install -y curl

# 3. 빌드 결과물 복사
COPY build/libs/*.jar app.jar

# 4. 포트 설정
EXPOSE 8080

# 5. 실행 명령어
CMD ["java", "-jar", "app.jar"]
