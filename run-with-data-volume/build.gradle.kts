import de.gesellix.docker.engine.EngineResponse
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
    containerConfiguration.putAll(
      mapOf(
        "Cmd" to listOf("-"),
        "Image" to "gesellix/run-with-data-volumes",
        "HostConfig" to mapOf(
          "Binds" to listOf("$volumeDir:/data")
        )
      )
    )
  }
  val runContainerWithDataVolume = register<DockerRunTask>("runContainerWithDataVolume") {
    dependsOn(createDataContainer)
    imageName.set("gesellix/docker-client-testimage")
    containerName.set("service-example")
    containerConfiguration.putAll(
      mapOf(
        "Cmd" to listOf("true"),
        "HostConfig" to mapOf(
          "VolumesFrom" to listOf("data-volume")
        )
      )
    )
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
