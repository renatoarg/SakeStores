import org.gradle.api.tasks.Copy

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.dagger.hilt.android) apply false
    alias(libs.plugins.google.devtools.ksp) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.dokka) apply false
}

apply(from = "jacoco-report.gradle")

tasks.register<Copy>("copyDokkaDocsToRoot") {
    dependsOn(subprojects.mapNotNull { it.tasks.findByName("dokkaHtml") })

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    subprojects.forEach { subproject ->
        val dokkaOutputDir = File(subproject.buildDir, "dokka/html")
        val outputDir = File(rootDir, "docs/${subproject.name}")

        from(dokkaOutputDir)
        into(outputDir)
    }
}
