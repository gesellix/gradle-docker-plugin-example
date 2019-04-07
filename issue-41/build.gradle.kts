import de.gesellix.gradle.docker.tasks.GenericDockerTask

docker {
  dockerHost = System.getProperty("DOCKER_HOST") ?: "https://192.168.99.100:2376"
  setCertPath(System.getProperty("docker.cert.path") ?: "${System.getProperty("user.home")}/.docker/machine/machines/default")
}

tasks.register<GenericDockerTask>("verifyDockerVersion") {
  doFirst {
    logger.lifecycle(certPath)
    val regexOld = """^1\.10\.""".toRegex()
    val regexNew = """^\d{2,}\.\d+\.""".toRegex()
    val version = (dockerClient.version().content as Map<String, Any>)["Version"] as String
    if (!(regexOld matches version || regexNew matches version)) {
      throw GradleException("Requires Docker 1.10+")
    }
  }
}
