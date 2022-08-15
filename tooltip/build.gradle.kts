/*
 * Copyright 2022 Patrick Goldinger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Suppress needed until https://youtrack.jetbrains.com/issue/KTIJ-19369 is fixed
@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.agp.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
}

val pgComposeCompileSdk: String by project
val pgComposeMinSdk: String by project
val pgComposeTargetSdk: String by project

val pgComposeMavenGroupId: String by project
val pgComposeJitpackGroupId: String by project
val pgComposeVersion: String by project

android {
    compileSdk = pgComposeCompileSdk.toInt()

    defaultConfig {
        minSdk = pgComposeMinSdk.toInt()
        targetSdk = pgComposeTargetSdk.toInt()
        consumerProguardFiles("proguard-rules.pro")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
    }

    sourceSets {
        maybeCreate("main").apply {
            java {
                srcDirs("src/main/kotlin")
            }
        }
    }

    publishing {
        singleVariant("release")
    }
}

dependencies {
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)

    debugImplementation(libs.androidx.compose.ui.tooling)
}

val sourcesJar = tasks.register<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(android.sourceSets.getByName("main").java.srcDirs)
}

group = pgComposeJitpackGroupId
version = pgComposeVersion

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("composeTooltipRelease").apply {
                from(components.findByName("release"))
                artifact(sourcesJar)

                groupId = pgComposeMavenGroupId
                artifactId = "compose-tooltip"
                version = pgComposeVersion

                pom {
                    name.set("Compose Tooltip")
                    description.set("Tooltip modifier which shows a tooltip which looks and feels like the Android framework tooltip.")
                    url.set("https://patrickgold.dev/compose-tooltip")
                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0")
                        }
                    }
                    developers {
                        developer {
                            id.set("patrickgold")
                            name.set("Patrick Goldinger")
                            email.set("patrick@patrickgold.dev")
                        }
                    }
                    scm {
                        connection.set("scm:git:https://github.com/patrickgold/compose-tooltip/")
                        developerConnection.set("scm:git:https://github.com/patrickgold/compose-tooltip/")
                        url.set("https://github.com/patrickgold/compose-tooltip/")
                    }
                }
            }
        }
    }
}
