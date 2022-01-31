import de.gesellix.docker.authentication.AuthConfig
import de.gesellix.docker.engine.EngineResponse
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
    classpath("de.gesellix:docker-client:2022-01-29T20-50-00")
    classpath(localGroovy())
  }
}

// works with Gradle 2.1+, for the old configuration see http://plugins.gradle.org/plugin/de.gesellix.docker
plugins {
  id("com.github.ben-manes.versions") version "0.41.0"
  id("net.ossindex.audit") version "0.4.11"
  id("de.gesellix.docker") version "2022-01-31T22-07-00"
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

  wrapper {
    gradleVersion = "7.3.3"
    distributionType = Wrapper.DistributionType.ALL
  }
}
