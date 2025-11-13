# 1️⃣ Base image có JDK 21
FROM eclipse-temurin:21-jdk-jammy

# 2️⃣ Thiết lập thư mục làm việc
WORKDIR /app

# 3️⃣ Copy Maven wrapper và pom để cache dependencies
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# 4️⃣ Đảm bảo mvnw có quyền executable
RUN chmod +x mvnw

# 5️⃣ Tải dependency offline
RUN ./mvnw dependency:go-offline -B

# 6️⃣ Copy toàn bộ source code
COPY src ./src

# 7️⃣ Build ứng dụng, bỏ test
RUN ./mvnw clean package -DskipTests

# 8️⃣ Expose port ứng dụng
EXPOSE 8097
# 10️⃣ Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "target/Chat-AL-0.0.1-SNAPSHOT.jar"]
