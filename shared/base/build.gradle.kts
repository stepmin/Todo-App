plugins {
    alias(libs.plugins.mateeStarter.kmm.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

android {
    namespace = "kmp.shared.base"
}

multiplatformResources {
    resourcesPackage.set("kmp.shared.base")
}

ktlint {
    filter {
        exclude { entry ->
            entry.file.toString().contains("generated")
        }
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    ksp(libs.room.compiler)
}
