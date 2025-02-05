plugins {
    alias(libs.plugins.mateeStarter.kmm.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.compose.compiler)
    id("dev.mokkery") version "2.4.0"
}

android {
    namespace = "kmp.shared.samplecomposemultiplatform"
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
    commonMainImplementation(project(":shared:sample"))
    commonMainImplementation(project(":shared:samplesharedviewmodel"))

    commonMainImplementation(compose.runtime)
    commonMainImplementation(compose.foundation)
    commonMainImplementation(compose.material)
    @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
    commonMainImplementation(compose.components.resources)
    commonMainImplementation(compose.components.uiToolingPreview)
    ktlintRuleset(libs.ktlint.composeRules)



//    testImplementation(libs.kotlin.test)
//    testImplementation(libs.koin.test)
//    testImplementation(libs.kotlinx.coroutines.test)

//    testImplementation(kotlin("test"))
}
