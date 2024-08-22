plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.jobsearch_test"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.example.jobsearch_test"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(project(":core:navigation"))
    implementation(project(":core:ui"))
    implementation(project(":core:utils"))

    implementation(project(":data"))
    implementation(project(":data_api"))
    implementation(project(":domain_models"))

    implementation(project(":features:entrance"))
    implementation(project(":features:entrance_api"))
    implementation(project(":features:main"))
    implementation(project(":features:main_api"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.google.material)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

}