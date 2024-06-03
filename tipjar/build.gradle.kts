plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.apt)
  alias(libs.plugins.kotlin.parcelize)
  alias(libs.plugins.spotless)
}

android {
  namespace = "com.bitcoin.tipjar"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.bitcoin.tipjar"
    minSdk = 24
    targetSdk = 34
    versionCode = 2
    versionName = "1.1"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables {
      useSupportLibrary = true
    }
  }

  signingConfigs {

    getByName("debug") {
      storeFile = rootProject.file("release/debug.keystore")
      storePassword = "android"
      keyAlias = "androiddebugkey"
      keyPassword = "android"
    }

    create("release") {
      if (rootProject.file("release/release.jks").exists()) {
        storeFile = rootProject.file("release/release.jks")
        keyAlias = properties["KEY_ALIAS"]?.toString() ?: ""
        keyPassword = properties["KEY_PASSWORD"]?.toString() ?: ""
        storePassword = properties["STORE_PASSWORD"]?.toString() ?: ""
      }
    }
  }

  buildTypes {
    val baseUrl: String = properties["BASE_URL"]?.toString() ?: "\"https://raw.githubusercontent.com/castrokingjames/api/main/\""

    debug {
      signingConfig = signingConfigs["debug"]
      buildConfigField("String", "BASE_URL", baseUrl)
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro",
      )
    }

    release {
      signingConfig = signingConfigs.findByName("release") ?: signingConfigs["debug"]
      isShrinkResources = true
      isMinifyEnabled = true
      buildConfigField("String", "BASE_URL", baseUrl)
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro",
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
    compose = true
    buildConfig = true
  }

  composeOptions {
    kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
  }

  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {
  implementation(projects.timber)
  implementation(projects.ui.common)
  implementation(projects.ui.compose)
  implementation(projects.ui.viewmodel)
  implementation(projects.di.annotation)

  implementation(projects.model)
  implementation(projects.domain)
  implementation(projects.data)
  implementation(projects.datasource.local)
  implementation(projects.datasource.remote)

  implementation(libs.dagger)
  kapt(libs.dagger.compiler)
}
