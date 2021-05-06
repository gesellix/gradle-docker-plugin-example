import de.gesellix.gradle.docker.tasks.DockerBuildTask

tasks.register<DockerBuildTask>("buildImage") {
  imageName.set("foo")
  buildContextDirectory.set(file("."))
}
