# Stage 1 — Backend
FROM openjdk:22-jdk-slim AS backend
WORKDIR /app
COPY email-writer-kt/target/*.jar app.jar

# Stage 2 — Frontend
FROM nginx:alpine AS frontend
COPY email-frontend-project/dist /usr/share/nginx/html

# Final stage
FROM openjdk:22-jdk-slim
WORKDIR /app
COPY --from=backend /app/app.jar .
COPY --from=frontend /usr/share/nginx/html /app/frontend

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
