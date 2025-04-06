plugins {
    java
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.elementcraft"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.panda-lang.org/releases")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")

    compileOnly("com.github.MilkBowl:VaultAPI:1.7")

    // LiteCommands (`implementation` + `shadow`)
    implementation("dev.rollczi:litecommands-bukkit:3.9.7")
    shadow("dev.rollczi:litecommands-bukkit:3.9.7")

    implementation("org.spongepowered:configurate-hocon:4.1.2")
    shadow("org.spongepowered:configurate-hocon:4.1.2")

    // Hibernate
    implementation("org.hibernate.orm:hibernate-core:6.3.1.Final")

    // JDBC-драйвер для MySQL
    implementation("mysql:mysql-connector-java:8.0.33")

    // JPA API
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")

    // Валидация, если используешь @NotNull и т.п.
    implementation("jakarta.validation:jakarta.validation-api:3.0.2")

    // Логгер
    implementation("org.jboss.logging:jboss-logging:3.5.0.Final")
    

    // SLF4J (логирование)
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("org.slf4j:slf4j-simple:2.0.9")
}

java {
    val javaVersion = JavaVersion.toVersion(16)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion.set(JavaLanguageVersion.of(16))
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    if (JavaVersion.current().isJava10Compatible) {
        options.release.set(16)
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

tasks.shadowJar {
    archiveBaseName.set(project.name)
    archiveClassifier.set("")
    archiveVersion.set("")

    relocate("dev.rollczi.litecommands", "org.elementcraft.libs.litecommands")
    relocate("org.spongepowered.configurate", "org.elementcraft.libs.configurate")
}

tasks.register<Copy>("copyJar") {

    from(layout.buildDirectory.file("libs/${project.name}.jar"))
    into("/home/locb/_www/mc/mcd_1.16.5/plugins/")
    rename { "${project.name}.jar" }

    doLast {
        println("JAR file successfully copied to /plugins/")
    }
}

tasks.build {
    dependsOn(tasks.shadowJar)
    finalizedBy(tasks.named("copyJar"))
}