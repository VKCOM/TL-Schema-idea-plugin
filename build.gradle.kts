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

// Dependencies are managed with Gradle version catalog - read more: https://docs.gradle.org/current/userguide/version_catalogs.html
dependencies {
    testImplementation(libs.junit)
    testImplementation(libs.opentest4j)

    // IntelliJ Platform Gradle Plugin Dependencies Extension - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-dependencies-extension.html
    intellijPlatform {
        intellijIdeaCommunity(providers.gradleProperty("platformVersion"))

        // Plugin Dependencies. Uses `platformBundledPlugins` property from the gradle.properties file for bundled IntelliJ Platform plugins.
        bundledPlugins(providers.gradleProperty("platformBundledPlugins").map { it.split(',') })

        // Plugin Dependencies. Uses `platformPlugins` property from the gradle.properties file for plugin from JetBrains Marketplace.
        plugins(providers.gradleProperty("platformPlugins").map { it.split(',') })

        // Module Dependencies. Uses `platformBundledModules` property from the gradle.properties file for bundled IntelliJ Platform modules.
        bundledModules(providers.gradleProperty("platformBundledModules").map { it.split(',') })

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

// Configure Gradle Kover Plugin - read more: https://kotlin.github.io/kotlinx-kover/gradle-plugin/#configuration-details
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
    dependsOn(generateTLSchemaLexer)
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
    dependsOn(generateTL2Lexer)
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
        dependsOn(generateTLSchemaParser, generateTL2Parser)
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
