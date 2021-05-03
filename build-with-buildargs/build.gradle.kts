import de.gesellix.gradle.docker.tasks.DockerBuildTask

tasks.register<DockerBuildTask>("buildImage") {
  setImageName("buildarg-example")
  setBuildContextDirectory(file("."))
  setBuildParams(
    mapOf(
      "rm" to true,
      "buildargs" to mapOf("an_argument" to "the value")
    )
  )
}
