import org.jetbrains.changelog.Changelog
import org.jetbrains.grammarkit.tasks.GenerateLexerTask
import org.jetbrains.grammarkit.tasks.GenerateParserTask
import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java") // Java support
    alias(libs.plugins.kotlin) // Kotlin support
    alias(libs.plugins.intelliJPlatform) // IntelliJ Platform Gradle Plugin
    alias(libs.plugins.changelog) // Gradle Changelog Plugin
    alias(libs.plugins.kover) // Gradle Kover Plugin
    alias(libs.plugins.grammarkit) // Gradle Grammar-Kit Plugin
}

group = providers.gradleProperty("pluginGroup").get()
version = providers.gradleProperty("pluginVersion").get()

// Set the JVM language level used to build the project.
kotlin {
    jvmToolchain(21)
}

// Configure project's dependencies
repositories {
    mavenCentral()

    // IntelliJ Platform Gradle Plugin Repositories Extension - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-repositories-extension.html
    intellijPlatform {
        defaultRepositories()
    }
}

// Dependencies are managed with Gradle version catalog - read more: https://docs.gradle.org/current/userguide/platforms.html#sub:version-catalog
dependencies {
    testImplementation(libs.junit)
    testImplementation(libs.opentest4j)
    testImplementation(libs.konsist)

    // IntelliJ Platform Gradle Plugin Dependencies Extension - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-dependencies-extension.html
    intellijPlatform {
        create(providers.gradleProperty("platformType"), providers.gradleProperty("platformVersion"))

        // Plugin Dependencies. Uses `platformBundledPlugins` property from the gradle.properties file for bundled IntelliJ Platform plugins.
        bundledPlugins(providers.gradleProperty("platformBundledPlugins").map { it.split(',') })

        // Plugin Dependencies. Uses `platformPlugins` property from the gradle.properties file for plugin from JetBrains Marketplace.
        plugins(providers.gradleProperty("platformPlugins").map { it.split(',') })

        testFramework(TestFrameworkType.Platform)
    }
}

val generatedSourceDir = file("src/main/gen")

idea {
    module {
        generatedSourceDirs.add(generatedSourceDir)
    }
}

// Configure IntelliJ Platform Gradle Plugin - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-extension.html
intellijPlatform {
    pluginConfiguration {
        name = providers.gradleProperty("pluginName")
        version = providers.gradleProperty("pluginVersion")

        val changelog = project.changelog // local variable for configuration cache compatibility
        // Get the latest available change notes from the changelog file
        changeNotes = providers.gradleProperty("pluginVersion").map { pluginVersion ->
            with(changelog) {
                renderItem(
                    (getOrNull(pluginVersion) ?: getUnreleased())
                        .withHeader(false)
                        .withEmptySections(false),
                    Changelog.OutputType.HTML,
                )
            }
        }

        ideaVersion {
            sinceBuild = providers.gradleProperty("pluginSinceBuild")

            // We want to support all future versions, so we need to use a null provider here.
            // Without this, we cannot pass the `Plugin Verifier`.
            val pluginUntilBuild = providers.gradleProperty("pluginUntilBuild")
                .takeUnless { it.orNull.isNullOrBlank() } ?: provider { null }
            untilBuild = pluginUntilBuild
        }
    }

    pluginVerification {
        ides {
            recommended()
        }
    }
}

// Configure Gradle Changelog Plugin - read more: https://github.com/JetBrains/gradle-changelog-plugin
changelog {
    groups.empty()
    repositoryUrl = providers.gradleProperty("pluginRepositoryUrl")
}

sourceSets {
    main {
        java {
            srcDirs(generatedSourceDir)
        }
    }
}

// Configure Gradle Kover Plugin - read more: https://github.com/Kotlin/kotlinx-kover#configuration
kover {
    reports {
        total {
            xml {
                onCheck = true
            }
        }
    }
}

val deleteGenerateFiles = tasks.register<Delete>("deleteGenerateFiles") {
    delete(generatedSourceDir)
}

val generateTLSchemaLexer = tasks.register<GenerateLexerTask>("generateTLSchemaLexer") {
    sourceFile.set(file("src/main/grammars/TLShcema.flex"))
    targetOutputDir.set(file("src/main/gen/com/vk/tlschema"))
    purgeOldFiles.set(true)
}

val generateTLSchemaParser = tasks.register<GenerateParserTask>("generateTLSchemaParser") {
    sourceFile.set(file("src/main/grammars/TLSchema.bnf"))
    targetRootOutputDir.set(generatedSourceDir)
    pathToParser.set("/com/vk/tlschema/parser/TLSchemaParser.java")
    pathToPsiRoot.set("/com/vk/tlschema/psi")
    purgeOldFiles.set(true)
}

val generateTL2Lexer = tasks.register<GenerateLexerTask>("generateTL2Lexer") {
    sourceFile.set(file("src/main/grammars/TL2.flex"))
    targetOutputDir.set(file("src/main/gen/com/vk/tl2"))
    purgeOldFiles.set(true)
}

val generateTL2Parser = tasks.register<GenerateParserTask>("generateTL2Parser") {
    sourceFile.set(file("src/main/grammars/TL2.bnf"))
    targetRootOutputDir.set(generatedSourceDir)
    pathToParser.set("/com/vk/tl2/parser/TL2Parser.java")
    pathToPsiRoot.set("/com/vk/tl2/psi")
    purgeOldFiles.set(true)
}

tasks {
    clean {
        dependsOn(deleteGenerateFiles)
    }

    generateLexer {
        dependsOn(generateTLSchemaLexer, generateTL2Lexer)
        enabled = false
    }

    generateParser {
        dependsOn(generateLexer)
        mustRunAfter(generateTLSchemaParser, generateTL2Parser)
        enabled = false
    }

    withType<KotlinCompile>().configureEach {
        dependsOn(generateParser)
    }

    withType<JavaCompile>().configureEach {
        dependsOn(generateParser)
    }

    wrapper {
        gradleVersion = providers.gradleProperty("gradleVersion").get()
    }

    buildPlugin {
        archiveBaseName.set("tl-schema-support")
    }
}
