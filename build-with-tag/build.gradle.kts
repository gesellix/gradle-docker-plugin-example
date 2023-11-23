import de.gesellix.gradle.docker.tasks.DockerBuildTask

tasks {
  register<DockerBuildTask>("buildImage") {
    imageName.set("example:with-a-tag")
    buildContextDirectory.set(file("./docker/"))
  }
}
