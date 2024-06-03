plugins {
  alias(libs.plugins.kotlin.jvm)
}

dependencies {
  implementation(projects.timber)
  implementation(projects.model)
  implementation(projects.di.annotation)

  implementation(libs.kotlin.stdlib)
  implementation(libs.kotlin.coroutines)

  api(libs.retrofit)
  api(libs.retrofit.converter.gson)
  api(libs.retrofit.interceptor.logging)
  api(libs.gson)
}
