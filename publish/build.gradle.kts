import de.gesellix.gradle.docker.tasks.DockerPublishTask

tasks.register<DockerPublishTask>("buildAndPushImage") {
  imageName.set("gesellix/example")
  imageTag.set("latest")
//  authConfig.set(dockerClient.readDefaultAuthConfig())
  buildContextDirectory.set(file("./docker/"))
  targetRegistries.putAll(mapOf("dev" to "localhost:5000"))
}
