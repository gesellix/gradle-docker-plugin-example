import de.gesellix.gradle.docker.tasks.DockerBuildTask
import de.gesellix.gradle.docker.tasks.DockerRmTask
import de.gesellix.gradle.docker.tasks.DockerRmiTask
import de.gesellix.gradle.docker.tasks.DockerRunTask
import de.gesellix.gradle.docker.tasks.DockerStopTask

tasks {
  val rmImage = register<DockerRmiTask>("rmImage") {
    imageId.set("foo")
  }

  val buildImage = register<DockerBuildTask>("buildImage") {
    dependsOn(rmImage)
    imageName.set("foo")
    buildContextDirectory.set(file("./docker/"))
  }

  val stopContainer = register<DockerStopTask>("stopContainer") {
    dependsOn(buildImage)
    containerId.set("foo")
  }

  val rmContainer = register<DockerRmTask>("rmContainer") {
    dependsOn(stopContainer)
    containerId.set("foo")
  }

  register<DockerRunTask>("runContainer") {
    dependsOn(rmContainer)
    imageName.set("foo")
    containerName.set("foo")
  }
}
