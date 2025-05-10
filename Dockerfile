FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

RUN ./mvnw clean package -DskipTests

EXPOSE ${PORT}

# Copie o JAR para o container
COPY target/order-0.0.1-SNAPSHOT.jar /app/order.jar

# Defina o comando para rodar o JAR
ENTRYPOINT ["java", "-jar", "/app/order.jar"]