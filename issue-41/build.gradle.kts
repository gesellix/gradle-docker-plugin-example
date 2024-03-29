import de.gesellix.gradle.docker.tasks.GenericDockerTask

docker {
  dockerHost = System.getProperty("DOCKER_HOST") ?: "https://192.168.99.100:2376"
  certPath = System.getProperty("docker.cert.path") ?: "${System.getProperty("user.home")}/.docker/machine/machines/default"
}

tasks.register<GenericDockerTask>("verifyDockerVersion") {
  doFirst {
    logger.lifecycle(certPath.get())
    val regexOld = """^1\.10\.""".toRegex()
    val regexNew = """^\d{2,}\.\d+\..*""".toRegex()
    val version = dockerClient.version().content.version ?: ""
    if (!(regexOld matches version || regexNew matches version)) {
      throw GradleException("Requires Docker 1.10+, got ${version}.")
    }
  }
}
