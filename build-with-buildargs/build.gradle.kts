import de.gesellix.gradle.docker.tasks.DockerBuildTask

tasks.register<DockerBuildTask>("buildImage") {
  imageName.set("buildarg-example")
  buildContextDirectory.set(file("."))
  buildParams.putAll(
    mapOf(
      "rm" to true,
      "buildargs" to mapOf("an_argument" to "the value")
    )
  )
}
