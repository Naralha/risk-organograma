FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /workspace

COPY pom.xml .
RUN mvn --batch-mode dependency:go-offline

COPY src src
RUN mvn --batch-mode clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /app

RUN groupadd --system risk && useradd --system --gid risk risk
COPY --from=build --chown=risk:risk /workspace/target/risk.jar app.jar

USER risk
EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=5s --start-period=30s --retries=3 \
  CMD bash -c 'exec 3<>/dev/tcp/127.0.0.1/8080 && printf "GET /actuator/health/readiness HTTP/1.0\r\n\r\n" >&3 && grep -q "UP" <&3'

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "/app/app.jar"]
