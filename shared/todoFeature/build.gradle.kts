plugins {
    alias(libs.plugins.mateeStarter.kmm.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.compose.compiler)
    id("dev.mokkery") version "2.4.0"
}

android {
    namespace = "kmp.shared.taskList"
}

kotlin {
    // ... other target configurations (android, jvm, ios, etc.)

    sourceSets {
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))

                implementation(libs.kotlin.test)
                implementation(libs.kotlin.test.junit)
                implementation(libs.koin.test)
                implementation(libs.kotlinx.coroutines.test)
            }
        }
    }
}

ktlint {
    filter {
        exclude { entry ->
            entry.file.toString().contains("generated")
        }
    }
}

dependencies {
    commonMainImplementation(project(":shared:base"))

    commonMainImplementation(compose.runtime)
    commonMainImplementation(compose.foundation)
    commonMainImplementation(compose.material)
    @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
    commonMainImplementation(compose.components.resources)
    commonMainImplementation(compose.components.uiToolingPreview)
    ktlintRuleset(libs.ktlint.composeRules)

    // Remove these two dependencies for the iOS swipe back navigation to work
    commonMainImplementation(libs.androidX.navigation)
    commonMainImplementation(libs.compose.materialNavigation)

    commonMainImplementation(libs.mokoResources.compose)
}