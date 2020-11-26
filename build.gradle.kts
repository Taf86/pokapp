buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.2")
        classpath(kotlin("gradle-plugin", "1.3.72"))
        classpath("com.bugsnag:bugsnag-android-gradle-plugin:4.7.4")
        classpath("com.google.gms:google-services:4.3.3")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.1")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("https://jitpack.io")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}


// Top-level build file where you can add configuration options common to all sub-projects/modules.
//buildscript {
//    ext.kotlin_version = "1.3.72"
//    repositories {
//        google()
//        jcenter()
//    }
//    dependencies {
//        classpath "com.android.tools.build:gradle:4.0.2"
//        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
//
//        // NOTE: Do not place your application dependencies here; they belong
//        // in the individual module build.gradle files
//    }
//}
//
//allprojects {
//    repositories {
//        google()
//        jcenter()
//    }
//}
//
//task clean(type: Delete) {
//    delete rootProject.buildDir
//}