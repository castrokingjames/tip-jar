plugins {
  alias(libs.plugins.kotlin.jvm)
}

dependencies {
  implementation(projects.timber)
  implementation(projects.model)
  implementation(projects.di.annotation)

  implementation(libs.kotlin.stdlib)
  implementation(libs.kotlin.coroutines)
}
