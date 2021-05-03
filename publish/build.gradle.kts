import de.gesellix.gradle.docker.tasks.DockerPublishTask

tasks.register<DockerPublishTask>("buildAndPushImage") {
  setImageName("gesellix/example")
  setImageTag("latest")
//  authConfig.set(dockerClient.readDefaultAuthConfig())
  setBuildContextDirectory(file("./docker/"))
  setTargetRegistries(mapOf("dev" to "localhost:5000"))
}
