import de.gesellix.gradle.docker.tasks.DockerBuildTask
import de.gesellix.gradle.docker.tasks.DockerRmTask
import de.gesellix.gradle.docker.tasks.DockerRmiTask
import de.gesellix.gradle.docker.tasks.DockerRunTask
import de.gesellix.gradle.docker.tasks.DockerStopTask

tasks {
  val rmImage = register<DockerRmiTask>("rmImage") {
    setImageId("foo")
  }

  val buildImage = register<DockerBuildTask>("buildImage") {
    dependsOn(rmImage)
    setImageName("foo")
    setBuildContextDirectory(file("./docker/"))
  }

  val stopContainer = register<DockerStopTask>("stopContainer") {
    dependsOn(buildImage)
    setContainerId("foo")
  }

  val rmContainer = register<DockerRmTask>("rmContainer") {
    dependsOn(stopContainer)
    setContainerId("foo")
  }

  register<DockerRunTask>("runContainer") {
    dependsOn(rmContainer)
    setImageName("foo")
    setContainerName("foo")
  }
}
