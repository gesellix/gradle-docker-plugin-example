import de.gesellix.gradle.docker.tasks.DockerBuildTask

tasks {
  register<DockerBuildTask>("buildWithAuth") {
    imageName = "gesellix/example"
    setBuildContextDirectory(file("."))

    // Forced pull to test authentication at the remote registry.
    // Registry credentials should be found in the default config.
    buildParams = mutableMapOf("pull" to true)
  }
}
