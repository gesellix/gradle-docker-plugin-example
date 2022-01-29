import de.gesellix.gradle.docker.tasks.DockerBuildTask
import de.gesellix.gradle.docker.tasks.DockerLogsTask
import de.gesellix.gradle.docker.tasks.DockerRmTask
import de.gesellix.gradle.docker.tasks.DockerRmiTask
import de.gesellix.gradle.docker.tasks.DockerRunTask
import de.gesellix.gradle.docker.tasks.DockerStopTask

tasks {
  val stopContainer1 = register<DockerStopTask>("stopContainer1") {
    containerId.set("run-with-user")
  }
  val rmContainer1 = register<DockerRmTask>("rmContainer1") {
    dependsOn(stopContainer1)
    containerId.set("run-with-user")
  }
  val stopContainer2 = register<DockerStopTask>("stopContainer2") {
    containerId.set("run-with-user-example")
  }
  val rmContainer2 = register<DockerRmTask>("rmContainer2") {
    dependsOn(stopContainer2)
    containerId.set("run-with-user-example")
  }
  val rmImage = register<DockerRmiTask>("rmImage") {
    dependsOn(rmContainer1, rmContainer2)
    imageId.set("run-with-user")
  }
  val buildImage = register<DockerBuildTask>("buildImage") {
    dependsOn(rmImage)
    imageName.set("run-with-user")
    buildContextDirectory.set(file("./docker/"))
  }
  val runContainer = register<DockerRunTask>("runContainer") {
    dependsOn(buildImage, rmContainer1, rmContainer2)
    imageName.set("run-with-user")
    containerName.set("run-with-user-example")
    containerConfiguration.get().apply {
      tty = true
      user = "root"
    }
  }
  register<DockerLogsTask>("printContainerLogs") {
    dependsOn(runContainer)
    containerId.set("run-with-user-example")
  }
}
