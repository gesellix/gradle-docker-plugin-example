import de.gesellix.docker.client.DockerClientImpl
import de.gesellix.gradle.docker.tasks.GenericDockerTask

buildscript {
  repositories {
    mavenLocal()
    maven {
      setUrl("https://plugins.gradle.org/m2/")
    }
  }
  dependencies {
    //    classpath "de.gesellix:gradle-docker-plugin:2017-10-05T20-48-17"
//    classpath "de.gesellix:gradle-docker-plugin:2017-12-28T22-48-35"
//    classpath "de.gesellix:gradle-docker-plugin:1.0.0-SNAPSHOT"
  }
}

//apply(plugin=de.gesellix.gradle.docker.DockerPlugin)

//docker {
//  dockerHost "http://10.30.0.129:2375"
//}
tasks.register<GenericDockerTask>("info") {
  dockerHost.set("http://10.30.0.129:2375")
  doLast {
    val client = dockerClient as DockerClientImpl
//    println((client.httpClient as OkDockerClient).dockerClientConfig.env.dockerHost)   // unix:///var/run/docker.sock
    println(client.dockerClientConfig.env.dockerHost)              // unix:///var/run/docker.sock
    println(client.env.dockerHost)                                 // http://10.30.0.129:2375
  }
}
