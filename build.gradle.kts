import de.gesellix.docker.authentication.AuthConfig
import de.gesellix.gradle.docker.tasks.DockerInfoTask
import de.gesellix.gradle.docker.tasks.DockerPingTask
import de.gesellix.gradle.docker.tasks.DockerVersionTask

buildscript {
  repositories {
    mavenCentral()
    gradlePluginPortal()
  }

  dependencies {
    classpath("de.gesellix:docker-client:2022-05-24T07-36-00")
    classpath(localGroovy())
  }
}

// works with Gradle 2.1+, for the old configuration see http://plugins.gradle.org/plugin/de.gesellix.docker
plugins {
  id("com.github.ben-manes.versions") version "0.42.0"
  id("net.ossindex.audit") version "0.4.11"
  id("de.gesellix.docker") version "2022-04-23T15-43-00"
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
      println(result.content)
    }
  }

  register<DockerVersionTask>("dockerVersion") {
    doLast {
      println(version.content)
    }
  }

  register<DockerInfoTask>("dockerInfo") {
    doLast {
      println(info.content)
    }
  }

  wrapper {
    gradleVersion = "7.4.2"
    distributionType = Wrapper.DistributionType.BIN
    distributionSha256Sum = "29e49b10984e585d8118b7d0bc452f944e386458df27371b49b4ac1dec4b7fda"
  }
}
