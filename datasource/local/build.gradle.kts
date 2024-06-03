plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.sqldelight)
}

android {
  namespace = "com.bitcoin.tipjar.datasource.local"
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
  api(libs.sqldelight.driver)
  api(libs.sqldelight.extension)
}

sqldelight {
  databases {
    create("Database") {
      packageName.set("com.bitcoin.tipjar.datasource.local.database")
      dialect("app.cash.sqldelight:sqlite-3-25-dialect:2.0.2")
      version = Math.random()
    }
  }
}
