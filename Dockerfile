# Etapa 1: build da aplicação (com Maven instalado)
FROM eclipse-temurin:17-jdk as builder

# Instala o Maven
RUN apt-get update && apt-get install -y maven

WORKDIR /app
COPY . .

# Usa o Maven instalado
RUN mvn clean package -DskipTests

# Etapa 2: imagem leve para execução
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copia o JAR do módulo "rest"
COPY --from=builder /app/rest/target/rest-0.0.1-SNAPSHOT.jar app.jar

# Porta padrão do Spring Boot
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]