import de.gesellix.gradle.docker.tasks.DockerPullTask
import de.gesellix.gradle.docker.tasks.DockerPushTask

// fallback to index.docker.io
val registryHostname = null
// fallback to ~/.dockercfg
val dockerCfgFilename = null

tasks {
  register<DockerPushTask>("pushImageToPrivateRepo") {
    repositoryName = "gesellix/private-repo"
    authConfigPlain = dockerClient.readAuthConfig(registryHostname, dockerCfgFilename)
  }
  register<DockerPullTask>("pullImageFromRemoteServer") {
    imageName = "gesellix/private-repo"
    tag = "latest"
    authConfigPlain = dockerClient.readDefaultAuthConfig()
  }
}
