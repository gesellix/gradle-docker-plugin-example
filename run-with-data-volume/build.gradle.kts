import de.gesellix.docker.engine.EngineResponse
import de.gesellix.docker.remote.api.HostConfig
import de.gesellix.gradle.docker.tasks.DockerCreateTask
import de.gesellix.gradle.docker.tasks.DockerInspectContainerTask
import de.gesellix.gradle.docker.tasks.DockerRmTask
import de.gesellix.gradle.docker.tasks.DockerRunTask
import de.gesellix.gradle.docker.tasks.DockerStopTask

val volumeDir = "/tmp"

tasks {

  val createDataContainer = register<DockerCreateTask>("createDataContainer") {
    imageName.set("gesellix/docker-client-testimage")
    containerName.set("data-volume")
    containerConfiguration.get().apply {
      cmd = listOf("-")
      image = "gesellix/run-with-data-volumes"
      hostConfig = HostConfig().apply {
        binds = listOf("$volumeDir:/data")
      }
    }
  }
  val runContainerWithDataVolume = register<DockerRunTask>("runContainerWithDataVolume") {
    dependsOn(createDataContainer)
    imageName.set("gesellix/docker-client-testimage")
    containerName.set("service-example")
    containerConfiguration.get().apply {
      cmd = listOf("true")
      hostConfig = HostConfig().apply {
        volumesFrom = listOf("data-volume")
      }
    }
  }

  val inspectServiceContainer = register<DockerInspectContainerTask>("inspectServiceContainer") {
    dependsOn(runContainerWithDataVolume)
    containerId.set("service-example")

    doLast {
      logger.info("${(containerInfo as EngineResponse).content}")
    }
  }

  val stopServiceContainer = register<DockerStopTask>("stopServiceContainer") {
    dependsOn(inspectServiceContainer)
    containerId.set("service-example")
  }

  val rmServiceContainer = register<DockerRmTask>("rmServiceContainer") {
    dependsOn(stopServiceContainer)
    containerId.set("service-example")
  }

  val rmDataVolumeContainer = register<DockerRmTask>("rmDataVolumeContainer") {
    dependsOn(rmServiceContainer)
    containerId.set("service-example")
  }

  runContainerWithDataVolume.get().finalizedBy(rmDataVolumeContainer)
}
