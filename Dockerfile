FROM maven:3.10-eclipse-temurin-25 AS build

WORKDIR /build

COPY entellocore/pom.xml entellocore/pom.xml
COPY entellocore/src entellocore/src
RUN mvn -B -f entellocore/pom.xml install

COPY entellord/pom.xml entellord/pom.xml
COPY entellord/src entellord/src
RUN mvn -B -f entellord/pom.xml package

FROM eclipse-temurin:25-jre

RUN apt-get update \
    && apt-get install -y --no-install-recommends ca-certificates ffmpeg pipx python3 yt-dlp \
    && groupadd --system entellord \
    && useradd --system --gid entellord --home-dir /app --shell /usr/sbin/nologin entellord \
    && mkdir -p /app/config /opt/pipx \
    && chown -R entellord:entellord /app /opt/pipx

ENV PIPX_HOME=/opt/pipx
ENV PIPX_BIN_DIR=/usr/local/bin

RUN pipx install gallery-dl

WORKDIR /app
COPY --from=build --chown=entellord:entellord /build/entellord/target/entello.jar /app/app.jar

USER entellord

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
