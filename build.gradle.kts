// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false

    // ktlint
    id("org.jlleitschuh.gradle.ktlint") version "12.0.3"
}

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}
