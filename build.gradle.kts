plugins {
    java
}

group = "org.elementcraft"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/groups/public/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")

    // Hibernate
    implementation("org.hibernate:hibernate-core:6.3.1.Final")
    implementation("mysql:mysql-connector-java:8.0.33")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    implementation("jakarta.validation:jakarta.validation-api:3.0.2")
    testImplementation("org.hibernate:hibernate-testing:6.3.1.Final")

    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("org.slf4j:slf4j-simple:2.0.9")
}

val targetJavaVersion = 16

java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion.set(JavaLanguageVersion.of(targetJavaVersion))
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"

    if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible) {
        options.release.set(targetJavaVersion)
    }
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}

tasks.register<Copy>("copyJar") {
    from(layout.buildDirectory.file("libs/${project.name}-${version}.jar"))
    into("/home/locb/_www/mc/mcd_1.16.5/plugins/")
    rename { "${project.name}.jar" }

    doLast {
        println("JAR-файл успешно скопирован в /home/locb/_www/mc/mcd_1.16_5/plugins/")
    }
}

// Запуск копирования после сборки
tasks.build {
    finalizedBy(tasks.named("copyJar"))
}