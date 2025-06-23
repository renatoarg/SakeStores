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
    dependsOn(subprojects.map { it.tasks.named("dokkaHtml") })

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    subprojects.forEach { subproject ->
        val dokkaOutputDir = File(subproject.buildDir, "dokka/html")
        val targetDir = File(rootDir, "docs/${subproject.name}")

        from(dokkaOutputDir) {
            into(subproject.name)
        }
    }

    into("$rootDir/docs")

    doLast {
        val docsDir = File("$rootDir/docs")
        val moduleLinks = mutableListOf<String>()

        val modules = mapOf(
            "app" to "📱 App Module - Main application with UI themes",
            "feat-sake-list" to "🏪 Sake List Feature - Browse sake shops",
            "feat-sake-details" to "📋 Sake Details Feature - Detailed shop information",
            "domain" to "🏗️ Domain Module - Business logic and models",
            "data" to "💾 Data Module - Data access and repositories",
            "core" to "⚙️ Core Module - Shared utilities",
            "design-system" to "🎨 Design System - UI components"
        )

        modules.forEach { (moduleName, description) ->
            val moduleIndexFile = File(docsDir, "$moduleName/index.html")
            if (moduleIndexFile.exists()) {
                moduleLinks.add("""
                    <div class="module">
                        <h2>$description</h2>
                        <a href="$moduleName/index.html">View Documentation →</a>
                    </div>
                """.trimIndent())
            }
        }

        val indexContent = """
            <!DOCTYPE html>
            <html>
            <head>
                <title>SakeStores - API Documentation</title>
                <style>
                    body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif; margin: 40px; background: #f8f9fa; }
                    .container { max-width: 1200px; margin: 0 auto; background: white; padding: 40px; border-radius: 12px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); }
                    h1 { color: #2196F3; font-size: 2.5em; margin-bottom: 10px; }
                    .subtitle { color: #666; margin-bottom: 30px; font-size: 1.1em; }
                    .module { margin: 20px 0; padding: 24px; border: 1px solid #e9ecef; border-radius: 8px; transition: all 0.2s; }
                    .module:hover { border-color: #2196F3; transform: translateY(-2px); box-shadow: 0 4px 12px rgba(33,150,243,0.1); }
                    .module h2 { margin-top: 0; color: #333; font-size: 1.3em; }
                    .module a { color: #2196F3; text-decoration: none; font-weight: 600; }
                    .module a:hover { text-decoration: underline; }
                    .stats { background: #f8f9fa; padding: 20px; border-radius: 8px; margin: 20px 0; }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>🍶 SakeStores</h1>
                    <p class="subtitle">Complete API Documentation - Generated with KDoc</p>
                    
                    <div class="stats">
                        <strong>📊 Documentation Stats:</strong> ${moduleLinks.size} modules documented with comprehensive KDoc comments
                    </div>
                    
                    ${moduleLinks.joinToString("\n")}
                </div>
            </body>
            </html>
        """.trimIndent()

        File("$rootDir/docs/index.html").writeText(indexContent)
        println("✅ Generated unified documentation with ${moduleLinks.size} modules")
    }
}
