FROM bellsoft/liberica-runtime-container:jdk-21-stream-musl AS builder
WORKDIR /app
ADD . /app
RUN chmod +x mvnw
RUN ./mvnw clean package -Dmaven.test.skip=true

FROM bellsoft/liberica-runtime-container:jre-21-musl
WORKDIR /app

# Create directories for storage
RUN mkdir -p /app/thumbnails /app/downloads

# Environment variables
ENV THUMBNAIL_PATH=/app/thumbnails/
ENV DOWNLOAD_PATH=/app/downloads/

EXPOSE 8081

COPY --from=builder /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]