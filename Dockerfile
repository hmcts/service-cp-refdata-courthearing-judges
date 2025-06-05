# ---- Base image (default fallback) ----
ARG BASE_IMAGE
FROM ${BASE_IMAGE:-openjdk:21-jdk-slim}

# ---- Runtime arguments ----
ARG JAR_FILENAME=app.jar
ARG LIBS_FILE_PATH=build/libs
ENV JAR_FILENAME=$JAR_FILENAME
ENV JAR_FILE_PATH=$LIBS_FILE_PATH
ENV JAR_FULL_PATH=$LIBS_FILE_PATH/$JAR_FILENAME

# ---- Dependencies ----
RUN apt-get update \
    && apt-get install -y curl \
    && rm -rf /var/lib/apt/lists/*

# ---- Application files ----
COPY $JAR_FULL_PATH /opt/app/$JAR_FILENAME
COPY $LIBS_FILE_PATH/applicationinsights.json /opt/app/

# ---- Permissions ----
RUN chmod 755 /opt/app/$JAR_FILENAME

# ---- Runtime ----
EXPOSE 4550
CMD ["java", "-jar", "/opt/app/$JAR_FILENAME"]