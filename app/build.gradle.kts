import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(GradlePlugins.android)
    kotlin("android")
    kotlin("kapt")
    id(GradlePlugins.kotlinParcelize)
    id(GradlePlugins.hilt)
    id(GradlePlugins.safeArgs)
}

android {
    compileSdkVersion(Config.compileVersion)
    buildToolsVersion(Config.buildToolsVersion)
    defaultConfig {
        applicationId = "com.costular.decorit"
        minSdkVersion(Config.minSdk)
        targetSdkVersion(Config.targetSdk)
        versionCode = Config.versionCode
        versionName = Config.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["dagger.hilt.disableModulesHaveInstallInCheck"] = "true"
            }
        }
    }

    signingConfigs {
        create("release") {
            storeFile = rootProject.file("release.keystore")
            storePassword = System.getenv("ANDROID_KEYSTORE_PASSPHRASE")
            keyAlias = System.getenv("ANDROID_KEYSTORE_ALIAS")
            keyPassword = System.getenv("ANDROID_KEYSTORE_PASSPHRASE")
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
            buildConfigField(
                "String",
                "UNSPLASH_ACCESS_KEY",
                "\"${gradleLocalProperties(rootDir).getProperty("UNSPLASH_ACCESS_KEY")}\""
            )
        }
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            buildConfigField(
                "String",
                "UNSPLASH_ACCESS_KEY",
                "\"${gradleLocalProperties(rootDir).getProperty("UNSPLASH_ACCESS_KEY")}\""
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }

    packagingOptions {
        // Multiple dependency bring these files in. Exclude them to enable
        // our test APK to build (has no effect on our AARs)
        excludes += "/META-INF/AL2.0"
        excludes += "/META-INF/LGPL2.1"
    }
}

kapt {
    correctErrorTypes = true
    /*arguments {
        arg("room.schemaLocation", "$projectDir/schemas")
        arg("room.incremental", "true")
    }*/
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Deps.hilt)
    implementation(Deps.constraintLayout)
    implementation(Deps.material)
    implementation(Deps.core)
    implementation(Deps.appCompat)
    implementation(Deps.viewModel)
    implementation(Deps.coroutines)
    implementation(Deps.kotlin)
    implementation(Deps.timber)
    kapt(Deps.hiltCompiler)
    kapt(Deps.hiltJetpackCompiler)
    implementation(Deps.hiltJetpackViewModel)
    implementation(Deps.glide)
    kapt(Deps.glideCompiler)
    implementation(Deps.appInitializer)
    implementation(Deps.preferences)
    implementation(Deps.preferencesDataStore)
    implementation(Deps.retrofit)
    implementation(Deps.moshi)
    implementation(Deps.moshiRetrofit)
    kapt(Deps.moshiCompiler)
    implementation(Deps.lifecycleRuntime)
    implementation(Deps.lifecycleLiveData)
    implementation(Deps.lifecycleCommon)
    debugImplementation(Deps.chuckerDebug)
    releaseImplementation(Deps.chuckerRelease)
    implementation(Deps.flick)
    implementation(Deps.gestureViews)
    implementation(Deps.flowBindingAndroid)
    implementation(Deps.composeActivity)
    implementation(Deps.composeFoundation)
    implementation(Deps.composeRuntime)
    implementation(Deps.composeLayout)
    implementation(Deps.composeMaterial)
    implementation(Deps.composeMaterialIcons)
    implementation(Deps.composeUi)
    implementation(Deps.composeUiTooling)
    implementation(Deps.composeNavigation)
    implementation(Deps.accompanistCoil)
    implementation(Deps.hiltNavigationCompose)

    testImplementation(Deps.junit)
    testImplementation(Deps.coroutinesTest)
    testImplementation(Deps.turbine)
    testImplementation(Deps.truth)
    testImplementation(Deps.test)
    testImplementation(Deps.mockk)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.freeCompilerArgs += "-Xinline-classes"
}