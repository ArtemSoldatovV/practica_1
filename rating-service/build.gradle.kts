plugins {
    kotlin("jvm") version "1.8.0"
    id("org.springframework.boot") version "3.0.0"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

group = "org.example"
version = "0.5.0"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.exposed:exposed-core:0.41.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.41.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.41.1")
    implementation("org.jetbrains.exposed:exposed-java-time:0.41.1")
    implementation("org.jetbrains.exposed:exposed-sql:0.41.1")
    implementation("org.jetbrains.exposed:exposed-testcontainers:0.41.1")

    implementation ("org.springframework.kafka:spring-kafka:3.0.4")

    implementation ("org.springframework.boot:spring-boot-starter-security")

    implementation ("io.jsonwebtoken:jjwt:0.9.1")

    implementation("org.springframework.data:spring-data-jpa:3.5.3")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    implementation ("org.postgresql:postgresql:42.6.0")//для PostgreSQL
    implementation( "org.mindrot:jbcrypt:0.4")//для генерация хэша

    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("io.ktor:ktor-server-netty:2.0.0")
    implementation("io.ktor:ktor-server-core:2.0.0")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

tasks.bootJar {
    mainClass.set("org.example.UserService.Application") // укажите ваш основной класс
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
    }
}