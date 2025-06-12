# Etapa 1: build da aplicação (com Maven instalado)
FROM eclipse-temurin:17-jdk as builder

# Instala o Maven
RUN apt-get update && apt-get install -y maven

WORKDIR /app
COPY . .

# Compila o projeto do módulo evolution
RUN mvn -f evolution/pom.xml clean package -DskipTests

# Etapa 2: imagem leve para execução
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copia o JAR do módulo evolution
COPY --from=builder /app/evolution/target/evolution-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
