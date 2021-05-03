import de.gesellix.gradle.docker.tasks.DockerPullTask
import de.gesellix.gradle.docker.tasks.DockerPushTask

// fallback to index.docker.io
val registryHostname = null
// fallback to ~/.dockercfg
val dockerCfgFilename = null

tasks {
  register<DockerPushTask>("pushImageToPrivateRepo") {
    setRepositoryName("gesellix/private-repo")
    setAuthConfigPlain(dockerClient.readAuthConfig(registryHostname, dockerCfgFilename))
  }
  register<DockerPullTask>("pullImageFromRemoteServer") {
    setImageName("gesellix/private-repo")
    setTag("latest")
    setAuthConfigPlain(dockerClient.readDefaultAuthConfig())
  }
}
