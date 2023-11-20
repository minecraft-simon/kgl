plugins {
    kotlin("jvm") version "1.9.20"
    application // Used to define the main class and create an executable jar.
}

group = "net.khack"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral() // Repository for dependencies.
}

// LWJGL and JOML Versions
val lwjglVersion = "3.2.3"
val jomlVersion = "1.10.5"
val lwjglNatives = "natives-windows"

dependencies {
    // LWJGL Dependencies
    implementation(platform("org.lwjgl:lwjgl-bom:$lwjglVersion"))
    implementation("org.lwjgl:lwjgl")
    implementation("org.lwjgl:lwjgl-assimp")
    implementation("org.lwjgl:lwjgl-glfw")
    implementation("org.lwjgl:lwjgl-nfd")
    implementation("org.lwjgl:lwjgl-openal")
    implementation("org.lwjgl:lwjgl-opengl")
    implementation("org.lwjgl:lwjgl-stb")
    runtimeOnly("org.lwjgl:lwjgl::$lwjglNatives")
    runtimeOnly("org.lwjgl:lwjgl-assimp::$lwjglNatives")
    runtimeOnly("org.lwjgl:lwjgl-glfw::$lwjglNatives")
    runtimeOnly("org.lwjgl:lwjgl-nfd::$lwjglNatives")
    runtimeOnly("org.lwjgl:lwjgl-openal::$lwjglNatives")
    runtimeOnly("org.lwjgl:lwjgl-opengl::$lwjglNatives")
    runtimeOnly("org.lwjgl:lwjgl-stb::$lwjglNatives")

    // JOML
    implementation("org.joml:joml:$jomlVersion")

    // Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")

    // Testing Framework
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.8.10")

    // Add any other dependencies specific to your new project
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8) // Specifies the Java version toolchain.
}

application {
    mainClass.set("net.khack.MainKt") // Replace with your actual main class path.
}

// Customizing the jar task to include your manifest.json and other necessary attributes.

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes(
            "Main-Class" to application.mainClass.get(),
            "Minecraft-Version" to "1.18.1" // Replace with the compatible Minecraft version.
        )
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    into("META-INF") {
        from("src/main/resources/manifest.json") // Update this path as necessary.
    }
    // Include any other necessary files or resources here.
}