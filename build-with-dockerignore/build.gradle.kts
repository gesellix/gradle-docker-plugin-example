import de.gesellix.gradle.docker.tasks.DockerBuildTask

tasks.register<DockerBuildTask>("buildImage") {
  setImageName("foo")
  setBuildContextDirectory(file("."))
}
