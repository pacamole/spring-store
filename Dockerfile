# --- ESTÁGIO 1: BUILD ---
# Java Development Kit (JDK)
#mini SO com java embutido para compilar o código:
FROM eclipse-temurin:21-jdk AS builder 
# Workdir é o local onde o código será copiado e executado (container)
WORKDIR /app
# Copia arquivos do meu projeto para o container (computador | container)
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Permissões de executável para o mvnw
RUN chmod +x mvnw
# Baixa todas as dependências do projeto
RUN ./mvnw dependency:go-offline

COPY src ./src

# Compila o código e cria o .jar final
# -DskipTests ignora os testes unitários
RUN ./mvnw clean package -DskipTests

# -ESTÁGIO 2: RUN ---
# Java Runtime Environment (JRE) para rodar a aplicação
# Mais leve e seguro que o JDK. Hackers não conseguem compilar código malicioso
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Copia o jar compilado do estágio 1 e apelida como app.jar
# O resto do lixo é descartado com o --from=builder
COPY --from=builder /app/target/*.jar app.jar

# Define qual porta o container escuta (mesma da aplicação)
EXPOSE 8080

# Comando para rodar a aplicação:
# java -jar app.jar
ENTRYPOINT [ "java", "-jar", "app.jar"]

