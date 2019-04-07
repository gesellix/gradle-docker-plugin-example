import de.gesellix.gradle.docker.tasks.DockerBuildTask
import de.gesellix.gradle.docker.tasks.DockerRmTask
import de.gesellix.gradle.docker.tasks.DockerRmiTask
import de.gesellix.gradle.docker.tasks.DockerRunTask
import de.gesellix.gradle.docker.tasks.DockerStopTask

tasks {
  val rmImage = register<DockerRmiTask>("rmImage") {
    imageId = "foo"
  }

  val buildImage = register<DockerBuildTask>("buildImage") {
    dependsOn(rmImage)
    imageName = "foo"
    setBuildContextDirectory(file("./docker/"))
  }

  val stopContainer = register<DockerStopTask>("stopContainer") {
    dependsOn(buildImage)
    containerId = "foo"
  }

  val rmContainer = register<DockerRmTask>("rmContainer") {
    dependsOn(stopContainer)
    containerId = "foo"
  }

  register<DockerRunTask>("runContainer") {
    dependsOn(rmContainer)
    imageName = "foo"
    containerName = "foo"
  }
}
