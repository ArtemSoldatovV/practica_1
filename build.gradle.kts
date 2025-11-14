plugins {
    kotlin("jvm") version "1.8.0"
    id("org.springframework.boot") version "3.0.0"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

group = "org.example"
version = "0.2.1"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation ("org.springframework.boot:spring-boot-starter-security")

    implementation ("io.jsonwebtoken:jjwt:0.9.1")

    implementation("org.springframework.data:spring-data-jpa:3.5.3")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    implementation ("org.postgresql:postgresql:42.6.0")//для PostgreSQL
    implementation( "org.mindrot:jbcrypt:0.4")//для генерация хеша

    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("io.ktor:ktor-server-netty:2.0.0")
    implementation("io.ktor:ktor-server-core:2.0.0")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
    }
}