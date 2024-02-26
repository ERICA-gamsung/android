// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false

    // ktlint
    id("org.jlleitschuh.gradle.ktlint") version "12.0.3"

    // detekt
    id("io.gitlab.arturbosch.detekt") version("1.23.4")
}

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "io.gitlab.arturbosch.detekt")
}

detekt {
    toolVersion = "1.23.3"
    config.setFrom(file("config/detekt/detekt.yml"))
    buildUponDefaultConfig = true
}
