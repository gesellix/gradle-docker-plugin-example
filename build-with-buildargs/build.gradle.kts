import de.gesellix.gradle.docker.tasks.DockerBuildTask

tasks.register<DockerBuildTask>("buildImage") {
  imageName = "buildarg-example"
  setBuildContextDirectory(file("."))
  buildParams = mapOf(
      "rm" to true, "buildargs" to mapOf("an_argument" to "the value")
  )
}
