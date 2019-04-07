import de.gesellix.gradle.docker.tasks.DockerPublishTask

tasks.register<DockerPublishTask>("buildAndPushImage") {
  imageName = "gesellix/example"
  imageTag = "latest"
//  authConfigPlain = getDockerClient().readDefaultAuthConfig()
  setBuildContextDirectory(file("./docker/"))
  targetRegistries = mapOf("dev" to "localhost:5000")
}
