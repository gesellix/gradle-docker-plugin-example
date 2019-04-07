import de.gesellix.docker.engine.EngineResponse
import de.gesellix.gradle.docker.tasks.DockerCreateTask
import de.gesellix.gradle.docker.tasks.DockerInspectContainerTask
import de.gesellix.gradle.docker.tasks.DockerRmTask
import de.gesellix.gradle.docker.tasks.DockerRunTask
import de.gesellix.gradle.docker.tasks.DockerStopTask

val volumeDir = "/tmp"

tasks {

  val createDataContainer = register<DockerCreateTask>("createDataContainer") {
    imageName = "gesellix/docker-client-testimage"
    containerName = "data-volume"
    containerConfiguration = mapOf(
        "Cmd" to listOf("-"),
        "Image" to "gesellix/run-with-data-volumes",
        "HostConfig" to mapOf(
            "Binds" to listOf("$volumeDir:/data")
        )
    )
  }
  val runContainerWithDataVolume = register<DockerRunTask>("runContainerWithDataVolume") {
    dependsOn(createDataContainer)
    imageName = "gesellix/docker-client-testimage"
    containerName = "service-example"
    containerConfiguration = mapOf(
        "Cmd" to listOf("true"),
        "HostConfig" to mapOf(
            "VolumesFrom" to listOf("data-volume")
        )
    )
  }

  val inspectServiceContainer = register<DockerInspectContainerTask>("inspectServiceContainer") {
    dependsOn(runContainerWithDataVolume)
    containerId = "service-example"

    doLast {
      logger.info("${(containerInfo as EngineResponse).content}")
    }
  }

  val stopServiceContainer = register<DockerStopTask>("stopServiceContainer") {
    dependsOn(inspectServiceContainer)
    containerId = "service-example"
  }

  val rmServiceContainer = register<DockerRmTask>("rmServiceContainer") {
    dependsOn(stopServiceContainer)
    containerId = "service-example"
  }

  val rmDataVolumeContainer = register<DockerRmTask>("rmDataVolumeContainer") {
    dependsOn(rmServiceContainer)
    containerId = "data-volume"
  }

  runContainerWithDataVolume.get().finalizedBy(rmDataVolumeContainer)
}
