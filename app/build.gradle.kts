import org.gradle.kotlin.dsl.kotlin

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-android-extensions")
    id("androidx.navigation.safeargs.kotlin")
}
android {
    compileSdkVersion(29)
    buildToolsVersion("29.0.3")

    defaultConfig {
        applicationId = "com.dc.pokapp"
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("debug") {

        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                file("proguard-rules.pro")
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")

    //KOTLIN
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.72")

    //ANDROIDX
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.1")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.1")

    //MATERIAL
    implementation("com.google.android.material:material:1.3.0-alpha03")

    //OKHTTP
    implementation("com.squareup.okhttp3:okhttp:4.7.1")

    //RETROFIT
    implementation("com.squareup.retrofit2:retrofit:2.8.1")
    implementation("com.squareup.retrofit2:converter-gson:2.8.1")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.8.1")
    implementation("com.squareup.retrofit2:converter-moshi:2.4.0")

    //MOSHI
    implementation("com.squareup.moshi:moshi:1.11.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.11.0")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.11.0")

    //ROOM
    implementation("androidx.room:room-runtime:2.2.5")
    kapt("androidx.room:room-compiler:2.2.5")
    implementation("androidx.room:room-ktx:2.2.5")

    //GLIDE
    implementation("com.github.bumptech.glide:glide:4.11.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.11.0")

    //KOIN
    implementation("org.koin:koin-core:2.1.0")
    implementation("org.koin:koin-android:2.1.0")
    implementation("org.koin:koin-androidx-scope:2.1.0")
    implementation("org.koin:koin-androidx-viewmodel:2.1.0")
    implementation("org.koin:koin-androidx-fragment:2.1.0")

    //PAGING
    implementation("androidx.paging:paging-runtime:3.0.0-alpha09")

    //UNIFLOW
    implementation("io.uniflow:uniflow-core:0.11.6")
    implementation("io.uniflow:uniflow-android:0.11.6")
    implementation("io.uniflow:uniflow-androidx:0.11.6")

}