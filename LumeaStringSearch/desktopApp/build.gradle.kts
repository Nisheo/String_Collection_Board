import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

dependencies {
    implementation(project(":shared"))

    implementation(compose.desktop.currentOs)
    implementation(libs.kotlinx.coroutinesSwing)

    implementation(libs.compose.components.resources)

    implementation(libs.compose.uiToolingPreview)
}

compose.desktop {
    application {
        mainClass = "com.example.lumeastringsearch.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Lumea String Search"
            packageVersion = "1.0.0"
            description = "Search the Lumea localization string collection"
            vendor = "Philips"

            // Installer / bundle icons. Each OS demands its own container format,
            // so there is no single cross-platform path
            macOS {
                bundleID = "com.example.lumeastringsearch"
                iconFile.set(project.file("icons/lumea.icns"))
            }
            windows {
                iconFile.set(project.file("icons/lumea.ico"))
                menuGroup = "Lumea"
                // Must stay constant across releases, otherwise each MSI installs
                // side-by-side instead of upgrading the previous installation.
                upgradeUuid = "54ed771a-4f0a-463e-9e2a-2ab5d41f4ea5"
            }
        }
    }
}