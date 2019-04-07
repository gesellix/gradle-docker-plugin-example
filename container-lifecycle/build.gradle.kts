import de.gesellix.gradle.docker.tasks.DockerContainerTask

tasks.register<DockerContainerTask>("reloadContainer") {
  //  dockerHost = "https://192.168.99.100:2376"
  containerName = "a_unique_name"
  targetState = DockerContainerTask.State.RELOADED

  image = "gesellix/docker-client-testimage"
  tag = "latest"
  ports = listOf("8080:80")
  env = listOf("TMP=1")
  cmd = listOf("ping", "127.0.0.1")
  volumes = listOf("/tmp:/data:ro")
  extraHosts = listOf("dockerhost:127.0.0.1")
}
