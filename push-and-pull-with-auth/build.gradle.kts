import de.gesellix.gradle.docker.tasks.DockerPullTask
import de.gesellix.gradle.docker.tasks.DockerPushTask

// fallback to index.docker.io
val registryHostname = null
// fallback to ~/.dockercfg
val dockerCfgFilename = null

tasks {
  register<DockerPushTask>("pushImageToPrivateRepo") {
    repositoryName.set("gesellix/private-repo")
    authConfig.set(dockerClient.readAuthConfig(registryHostname, dockerCfgFilename))
  }
  register<DockerPullTask>("pullImageFromRemoteServer") {
    imageName.set("gesellix/private-repo")
    imageTag.set("latest")
    authConfig.set(dockerClient.readDefaultAuthConfig())
  }
}
