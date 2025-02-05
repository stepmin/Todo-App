package config

import extensions.libs
import extensions.testImplementation
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureTests() {
    dependencies {
        testImplementation(libs.junit)
        testImplementation(libs.konsist)

//        testImplementation(libs.kotlin.test)
//        testImplementation(libs.kotlin.test.junit)
//        testImplementation(libs.koin.test)
//        testImplementation(libs.kotlinx.coroutines.test)
    }
}
