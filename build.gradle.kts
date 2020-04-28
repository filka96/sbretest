plugins {
    id("java")
    id("org.springframework.boot") version "2.2.6.RELEASE"
    id("net.nemerosa.versioning") version "2.13.1"
}

group = "ru.sberbank"
version = versioning.info.full

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    maven("http://nexus.sigma.sbrf.ru:8099/nexus/content/repositories/gradle_plugins/")
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:2.2.6.RELEASE")
    implementation("org.springdoc:springdoc-openapi-ui:1.3.5")
}

springBoot {
    mainClassName = "ru.sberbank.test.TestApplication"
}

tasks.wrapper {
    gradleVersion = "6.3"
    distributionType = Wrapper.DistributionType.BIN
}
