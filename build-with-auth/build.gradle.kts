import de.gesellix.gradle.docker.tasks.DockerBuildTask

tasks {
  register<DockerBuildTask>("buildWithAuth") {
    imageName.set("gesellix/example")
    buildContextDirectory.set(file("."))

    // Forced pull to test authentication at the remote registry.
    // Registry credentials should be found in the default config.
    buildParams.set(mutableMapOf<String, Any>("pull" to true))
  }
}
