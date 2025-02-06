plugins {
    alias(libs.plugins.mateeStarter.kmm.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.compose.compiler)
}

android {
    namespace = "kmp.shared.samplecomposenavigation"
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

    // Remove these two dependencies for the iOS swipe back navigation to work
    commonMainImplementation(libs.androidX.navigation)
    commonMainImplementation(libs.compose.materialNavigation)

    commonMainImplementation(libs.mokoResources.compose)

    ktlintRuleset(libs.ktlint.composeRules)
}
