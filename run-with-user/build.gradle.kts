import de.gesellix.gradle.docker.tasks.DockerBuildTask
import de.gesellix.gradle.docker.tasks.DockerRmTask
import de.gesellix.gradle.docker.tasks.DockerRmiTask
import de.gesellix.gradle.docker.tasks.DockerRunTask
import de.gesellix.gradle.docker.tasks.DockerStopTask
import de.gesellix.gradle.docker.tasks.GenericDockerTask
import de.gesellix.util.IOUtils
import java.io.InputStream

tasks {
  val rmImage = register<DockerRmiTask>("rmImage") {
    setImageId("run-with-user")
  }
  val buildImage = register<DockerBuildTask>("buildImage") {
    dependsOn(rmImage)
    setImageName("run-with-user")
    setBuildContextDirectory(file("./docker/"))
  }
  val stopContainer = register<DockerStopTask>("stopContainer") {
    dependsOn(buildImage)
    setContainerId("run-with-user-example")
  }
  val rmContainer = register<DockerRmTask>("rmContainer") {
    dependsOn(stopContainer)
    setContainerId("run-with-user-example")
  }
  val runContainer = register<DockerRunTask>("runContainer") {
    dependsOn(rmContainer)
    setImageName("run-with-user")
    setContainerName("run-with-user-example")
    setContainerConfiguration(mapOf("Tty" to true, "User" to "root"))
  }
  register<GenericDockerTask>("printContainerLogs") {
    dependsOn(runContainer)
    doLast {
      val response = dockerClient.logs("run-with-user-example")
      logger.lifecycle(IOUtils.toString(response.stream as InputStream))
    }
  }
}
