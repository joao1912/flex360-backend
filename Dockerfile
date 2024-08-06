# Use uma imagem do Maven para construir a aplicação
FROM maven:3.8.5-openjdk-17 AS build

# Crie um diretório para a aplicação
RUN mkdir -p /app

# Defina o diretório de trabalho
WORKDIR /app

# Copie o arquivo pom.xml e outros arquivos de configuração
COPY pom.xml /app

# Baixe as dependências necessárias (usando o cache do Docker)
RUN mvn dependency:go-offline -B

# Copie o código-fonte da aplicação
COPY src /app/src

# Construa a aplicação
RUN mvn clean package -DskipTests

# Use uma imagem do OpenJDK para executar a aplicação
FROM openjdk:22-jdk-slim

# Crie um diretório para a aplicação
RUN mkdir /app

# Defina o diretório de trabalho
WORKDIR /app

# Copie o arquivo JAR da aplicação do estágio de build para o diretório /app
COPY --from=build /app/target/seu-arquivo.jar /app/app.jar

# Exponha a porta que a aplicação Spring Boot usa
EXPOSE 8080

# Comando para executar a aplicação
CMD ["java", "-jar", "app.jar"]
