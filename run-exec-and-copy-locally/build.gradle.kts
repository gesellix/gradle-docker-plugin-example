import de.gesellix.docker.client.container.ArchiveUtil
import de.gesellix.docker.engine.EngineResponse
import de.gesellix.gradle.docker.tasks.DockerCopyFromContainerTask
import de.gesellix.gradle.docker.tasks.DockerExecTask
import de.gesellix.gradle.docker.tasks.DockerRmTask
import de.gesellix.gradle.docker.tasks.DockerRunTask
import de.gesellix.gradle.docker.tasks.DockerStopTask
import de.gesellix.gradle.docker.tasks.GenericDockerTask
import de.gesellix.util.IOUtils
import java.io.FileOutputStream
import java.io.InputStream

tasks {
  val stopContainer = register<DockerStopTask>("stopContainer") {
    containerId.set("exec-example")
  }
  val rmContainer = register<DockerRmTask>("rmContainer") {
    dependsOn(stopContainer)
    containerId.set("exec-example")
  }
  val runContainer = register<DockerRunTask>("runContainer") {
    dependsOn(rmContainer)
    imageName.set("alpine:edge")
    containerName.set("exec-example")
    containerConfiguration.putAll(mapOf("Cmd" to listOf("ping", "127.0.0.1")))
  }
  val execInContainer = register<DockerExecTask>("execInContainer") {
    dependsOn(runContainer)

    containerId.set("exec-example")
    cmd.set("echo \"hallo\" > /test.txt && cat /test.txt")

    doLast {
      logger.info("${IOUtils.copy((result as EngineResponse).stream as InputStream, System.out)}")
    }
  }
  val stopContainerAfterExec = register<DockerStopTask>("stopContainerAfterExec") {
    containerId.set("exec-example")
  }
  val downloadArchiveFromContainer = register<DockerCopyFromContainerTask>("downloadArchiveFromContainer") {
    dependsOn(execInContainer)
    finalizedBy(stopContainerAfterExec)
    container.set("exec-example")
    sourcePath.set("/test.txt")
  }
  register<GenericDockerTask>("extractSingleFile") {
    dependsOn(downloadArchiveFromContainer)

    doLast {
      val fileContent = ArchiveUtil().extractSingleTarEntry(downloadArchiveFromContainer.get().content.stream as InputStream, "test.txt")
      buildDir.mkdirs()
      val outputStream = FileOutputStream("$buildDir/test.txt")
      fileContent.inputStream().copyTo(outputStream)
      outputStream.close()
    }
  }
}
