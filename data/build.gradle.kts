plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.sqldelight)
}

android {
  namespace = "com.bitcoin.tipjar.data"
  compileSdk = 34

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  kotlinOptions {
    jvmTarget = JavaVersion.VERSION_17.toString()
  }
}

dependencies {
  implementation(projects.timber)
  implementation(projects.model)
  implementation(projects.domain)
  implementation(projects.datasource.local)
  implementation(projects.di.annotation)

  implementation(libs.kotlin.stdlib)
  implementation(libs.kotlin.coroutines)
}
