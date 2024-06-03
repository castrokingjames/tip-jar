plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
}

android {
  namespace = "com.bitcoin.tipjar.ui.compose"
  compileSdk = 34

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  kotlinOptions {
    jvmTarget = JavaVersion.VERSION_17.toString()
  }

  buildFeatures {
    compose = true
  }

  composeOptions {
    kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
  }
}

dependencies {
  api(platform(libs.compose.bom))
  api(libs.compose.activity)
  api(libs.compose.material3)
  api(libs.compose.navigation)
  api(libs.compose.foundation)
  api(libs.compose.ui)
  api(libs.compose.ui.graphics)
  api(libs.compose.ui.tooling)
  api(libs.compose.ui.tooling.preview)
  api(libs.compose.coil)

  api(libs.mavericks)
  api(libs.mavericks.compose)

  api(libs.androidx)

  implementation(projects.ui.common)
}
