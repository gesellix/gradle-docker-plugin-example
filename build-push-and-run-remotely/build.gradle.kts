import de.gesellix.docker.remote.api.HostConfig
import de.gesellix.docker.remote.api.PortBinding
import de.gesellix.gradle.docker.tasks.DockerBuildTask
import de.gesellix.gradle.docker.tasks.DockerPsTask
import de.gesellix.gradle.docker.tasks.DockerPullTask
import de.gesellix.gradle.docker.tasks.DockerPushTask
import de.gesellix.gradle.docker.tasks.DockerRmTask
import de.gesellix.gradle.docker.tasks.DockerRunTask
import de.gesellix.gradle.docker.tasks.DockerStopTask
import groovy.json.JsonOutput.prettyPrint
import groovy.json.JsonOutput.toJson

val remoteDockerHost = "https://192.168.99.100:2376"

tasks {
  val buildImage = register<DockerBuildTask>("buildImage") {
    imageName.set("gesellix/example")
    buildContextDirectory.set(file("./docker/"))
  }

  val pushImage = register<DockerPushTask>("pushImage") {
    dependsOn(buildImage)
    repositoryName.set("gesellix/example")
    registry.set("localhost:5000")
  }

  val pullImageOnRemoteServer = register<DockerPullTask>("pullImageOnRemoteServer") {
    dependsOn(pushImage)

    dockerHost.set(remoteDockerHost)
    imageName.set("gesellix/example")
    registry.set("localhost:5000")
  }

  val stopContainerOnRemoteServer = register<DockerStopTask>("stopContainerOnRemoteServer") {
    dependsOn(pullImageOnRemoteServer)

    dockerHost.set(remoteDockerHost)
    containerId.set("a_unique_name")
  }

  val rmOldContainerOnRemoteServer = register<DockerRmTask>("rmOldContainerOnRemoteServer") {
    dependsOn(stopContainerOnRemoteServer)

    dockerHost.set(remoteDockerHost)
    containerId.set("a_unique_name")
  }

  val runContainerOnRemoteServer = register<DockerRunTask>("runContainerOnRemoteServer") {
    dependsOn(rmOldContainerOnRemoteServer)

    dockerHost.set(remoteDockerHost)
    imageName.set("localhost:5000/gesellix/example")
    containerName.set("a_unique_name")
    containerConfiguration.get().exposedPorts = mapOf(
      "8889/tcp" to mapOf<String, Any>(),
      "9300/tcp" to mapOf()
    )
    containerConfiguration.get().hostConfig = HostConfig().apply {
      portBindings = mapOf(
        "8889/tcp" to listOf(
          PortBinding().apply {
            hostPort = "8889"
          }
        )
      )
    }
  }

  register<DockerPsTask>("listContainersOnRemoteServer") {
    dependsOn(runContainerOnRemoteServer)
    dockerHost.set(remoteDockerHost)

    doLast {
      println(prettyPrint(toJson(containers)))
    }
  }
}
