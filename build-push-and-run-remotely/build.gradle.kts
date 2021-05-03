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
    setImageName("gesellix/example")
    setBuildContextDirectory(file("./docker/"))
  }

  val pushImage = register<DockerPushTask>("pushImage") {
    dependsOn(buildImage)
    setRepositoryName("gesellix/example")
    setRegistry("localhost:5000")
  }

  val pullImageOnRemoteServer = register<DockerPullTask>("pullImageOnRemoteServer") {
    dependsOn(pushImage)

    setDockerHost(remoteDockerHost)
    setImageName("gesellix/example")
    setRegistry("localhost:5000")
  }

  val stopContainerOnRemoteServer = register<DockerStopTask>("stopContainerOnRemoteServer") {
    dependsOn(pullImageOnRemoteServer)

    setDockerHost(remoteDockerHost)
    setContainerId("a_unique_name")
  }

  val rmOldContainerOnRemoteServer = register<DockerRmTask>("rmOldContainerOnRemoteServer") {
    dependsOn(stopContainerOnRemoteServer)

    setDockerHost(remoteDockerHost)
    setContainerId("a_unique_name")
  }

  val runContainerOnRemoteServer = register<DockerRunTask>("runContainerOnRemoteServer") {
    dependsOn(rmOldContainerOnRemoteServer)

    setDockerHost(remoteDockerHost)
    setImageName("localhost:5000/gesellix/example")
    setContainerName("a_unique_name")
    setContainerConfiguration(
      mapOf<String, Any>(
        "ExposedPorts" to mapOf(
          "8889/tcp" to mapOf(),
          "9300/tcp" to mapOf<String, Any>()
        ),
        "HostConfig" to mapOf(
          "PortBindings" to mapOf(
            "8889/tcp" to listOf(
              mapOf("HostPort" to "8889")
            )
          )
        )
      )
    )
  }

  register<DockerPsTask>("listContainersOnRemoteServer") {
    dependsOn(runContainerOnRemoteServer)
    setDockerHost(remoteDockerHost)

    doLast {
      println(prettyPrint(toJson(containers)))
    }
  }
}
