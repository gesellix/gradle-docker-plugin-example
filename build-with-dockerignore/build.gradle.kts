import de.gesellix.gradle.docker.tasks.DockerBuildTask

tasks.register<DockerBuildTask>("buildImage") {
  imageName = "foo"
  setBuildContextDirectory(file("."))
}
