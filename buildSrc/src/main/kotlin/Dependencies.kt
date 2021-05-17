object Versions {
    val hiltNavigationCompose = "1.0.0-alpha01"
    val activityCompose = "1.3.0-alpha04"
    val kotlin = "1.4.32"
    val coroutines = "1.4.2"

    val core = "1.5.0-alpha02"
    val material = "1.4.0-alpha01"
    val appCompat = "1.3.0-beta01"
    val constraintLayout = "2.0.4"
    val lifecycle = "2.2.0"
    val fragment = "1.3.3"
    val navigation = "2.3.0-alpha06"
    val mockk = "1.10.0"
    val room = "2.2.6"
    val hilt = "2.35"
    val hiltJetpack = "1.0.0-beta01"
    val testJetpack = "2.1.0"
    val timber = "4.7.1"
    val initializer = "1.0.0-alpha01"
    val robolectric = "4.3.1"
    val testRunner = "1.2.0"
    val glide = "4.11.0"
    val retrofit = "2.9.0"
    val moshi = "1.9.3"
    val chucker = "3.3.0"
    val flick = "1.7.0"
    val gestureViews = "2.6.0"
    val flowBinding = "1.0.0-beta02"
    val compose = "1.0.0-beta06"
    val composeNavigation = "1.0.0-alpha10"
    val hiltWork = "1.0.0"
    val hiltJetpackViewModel = "1.0.0-alpha03"
    val accompanist = "0.9.1"
    val workManager = "2.5.0"

    val preferences = "1.1.1"
    val preferencesDataStore = "1.0.0-alpha01"
    val kaspresso = "1.1.0"
    val truth = "1.2.0"
    val turbine = "0.2.1"
}


object Deps {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    val core = "androidx.core:core-ktx:${Versions.core}"
    val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    val material = "com.google.android.material:material:${Versions.material}"
    val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    val hiltJetpackCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltJetpack}"
    val hiltJetpackViewModel =
        "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltJetpackViewModel}"
    val hiltNavigationCompose =
        "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationCompose}"
    val hiltWork = "androidx.hilt:hilt-work:${Versions.hiltWork}"
    val roomRuntinme = "androidx.room:room-runtime:${Versions.room}"
    val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"
    val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    val appInitializer = "androidx.startup:startup-runtime:${Versions.initializer}"
    val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    val preferences = "androidx.preference:preference-ktx:${Versions.preferences}"
    val preferencesDataStore = "androidx.datastore:datastore-preferences:${Versions.preferencesDataStore}"
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val moshiRetrofit = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    val moshi = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    val moshiCompiler = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
    val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime:${Versions.lifecycle}"
    val lifecycleCommon = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
    val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    val chuckerDebug = "com.github.chuckerteam.chucker:library:${Versions.chucker}"
    val chuckerRelease = "com.github.chuckerteam.chucker:library-no-op:${Versions.chucker}"
    val flick = "me.saket:flick:${Versions.flick}"
    val gestureViews = "com.alexvasilkov:gesture-views:${Versions.gestureViews}"
    val flowBindingAndroid =
        "io.github.reactivecircus.flowbinding:flowbinding-android:${Versions.flowBinding}"
    val composeActivity = "androidx.activity:activity-compose:${Versions.activityCompose}"
    val composeUi = "androidx.compose.ui:ui:${Versions.compose}"
    val composeFoundation = "androidx.compose.foundation:foundation:${Versions.compose}"
    val composeLayout = "androidx.compose.foundation:foundation-layout:${Versions.compose}"
    val composeRuntime = "androidx.compose.runtime:runtime:${Versions.compose}"
    val composeMaterial = "androidx.compose.material:material:${Versions.compose}"
    val composeMaterialIcons =
        "androidx.compose.material:material-icons-extended:${Versions.compose}"
    val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    val composeNavigation = "androidx.navigation:navigation-compose:${Versions.composeNavigation}"
    val accompanistCoil = "com.google.accompanist:accompanist-coil:${Versions.accompanist}"
    val workManager = "androidx.work:work-runtime-ktx:${Versions.workManager}"
    val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"

    val junit = "junit:junit:4.13"
    val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    val mockk = "io.mockk:mockk:${Versions.mockk}"
    val junitInstrumentation = "androidx.test.ext:junit:1.1.1"
    val truth = "androidx.test.ext:truth:${Versions.truth}"
    val test = "androidx.arch.core:core-testing:${Versions.testJetpack}"
    val testRunner = "androidx.test:runner:${Versions.testRunner}"
    val espresso = "androidx.test.espresso:espresso-core:3.2.0"
    val kaspresso = "com.kaspersky.android-components:kaspresso:${Versions.kaspresso}"
    val turbine = "app.cash.turbine:turbine:${Versions.turbine}"
}