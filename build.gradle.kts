import de.gesellix.docker.client.authentication.AuthConfig
import de.gesellix.docker.engine.EngineResponse
import de.gesellix.gradle.docker.tasks.DockerCleanupTask
import de.gesellix.gradle.docker.tasks.DockerInfoTask
import de.gesellix.gradle.docker.tasks.DockerPingTask
import de.gesellix.gradle.docker.tasks.DockerVersionTask
import groovy.json.JsonOutput.prettyPrint
import groovy.json.JsonOutput.toJson

buildscript {
  repositories {
    gradlePluginPortal()
  }

  dependencies {
    classpath("de.gesellix:docker-client:2021-08-07T20-46-00")
    classpath(localGroovy())
  }
}

// works with Gradle 2.1+, for the old configuration see http://plugins.gradle.org/plugin/de.gesellix.docker
plugins {
  id("com.github.ben-manes.versions") version "0.39.0"
  id("net.ossindex.audit") version "0.4.11"
  id("de.gesellix.docker") version "2021-05-05T22-41-13"
}

allprojects {
  apply(plugin = "base")
  apply(plugin = "de.gesellix.docker")

//  configure<de.gesellix.gradle.docker.DockerPluginExtension> {
  docker {
    //    dockerHost = System.env.DOCKER_HOST ?: "unix:///var/run/docker.sock"
//    dockerHost = System.env.DOCKER_HOST ?: "https://192.168.99.100:2376"
//    certPath = System.getProperty("docker.cert.path") ?: "${System.getProperty("user.home")}/.docker/machine/machines/default"
    authConfig = AuthConfig().apply {
      username = "gesellix"
      password = "-yet-another-password-"
      email = "tobias@gesellix.de"
      serveraddress = "https://index.docker.io/v1/"
    }
  }
}

tasks {
  register<DockerPingTask>("ping") {
    doLast {
      println(prettyPrint(toJson((result as EngineResponse).content)))
    }
  }

  register<DockerVersionTask>("dockerVersion") {
    doLast {
      println(prettyPrint(toJson(version)))
    }
  }

  register<DockerInfoTask>("dockerInfo") {
    doLast {
      println(prettyPrint(toJson(info)))
    }
  }

  register<DockerCleanupTask>("cleanupStorage") {
    shouldKeepContainer = KotlinClosure1<Map<String, Any>, Boolean>({
      logger.warn("container : $this")
//    container.Names.any { String name ->
//      name.replaceAll("^/", "").matches(".*data.*")
//    }
      true
    })
    shouldKeepVolume = KotlinClosure1<Map<String, Any>, Boolean>({
      logger.warn("volume : $this")
//    def keep = volume.Name.replaceAll("^/", "").matches(".*data.*")
//    if (keep) {
//      logger.warn("will keep ${volume}")
//    }
//    return keep
      true
    })
  }

  wrapper {
    gradleVersion = "6.8.3"
    distributionType = Wrapper.DistributionType.ALL
  }
}
