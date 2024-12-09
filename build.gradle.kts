plugins {
    kotlin("jvm") version "1.9.10"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
    maven { url = uri("https://dl.google.com/dl/android/maven2") }
}

dependencies {
    implementation(kotlin("stdlib"))
    // https://mvnrepository.com/artifact/com.google.api/gax-grpc
    implementation("com.google.api:gax-grpc:2.58.0")
    implementation("javax.mail:javax.mail-api:1.6.2")
    implementation("com.sun.mail:javax.mail:1.6.2")
    // https://mvnrepository.com/artifact/com.google.api-client/google-api-client-java6
    implementation("com.google.api-client:google-api-client-java6:2.1.4")
    // https://mvnrepository.com/artifact/com.google.api-client/google-api-client-jackson2
    implementation("com.google.api-client:google-api-client-jackson2:2.7.0")
    implementation("com.google.apis:google-api-services-tasks:v1-rev71-1.25.0")
    implementation("com.google.oauth-client:google-oauth-client:1.33.3")
    implementation("com.google.http-client:google-http-client-gson:1.40.1")
    // https://mvnrepository.com/artifact/com.google.api-client/google-api-client
    implementation("com.google.api-client:google-api-client:2.7.0")
    // https://mvnrepository.com/artifact/com.google.apis/google-api-services-gmail
    implementation("com.google.apis:google-api-services-gmail:v1-rev110-1.25.0")
    // https://mvnrepository.com/artifact/com.google.api-client/google-api-client-appengine
    implementation("com.google.api-client:google-api-client-appengine:2.7.0")

}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

application {
    mainClass.set("org.example.secretSanta.MainKt") // Replace with your main class package and name
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(17)) // Use Java 21
    }
}

sourceSets {
    main {
        kotlin.srcDirs("src/main/code")
    }
}