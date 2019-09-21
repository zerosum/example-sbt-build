package dev.zerosum.example

import akka.actor.ActorSystem
import akka.http.scaladsl.settings.ServerSettings
import dev.zerosum.example.server.HttpServer
import wvlet.airframe._

object Application {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("example")

    val design = newDesign
      .bind[ActorSystem]
      .toInstance(system)
      .add(controller.createControllerDesign)

    design.withSession { session =>
      session
        .build[HttpServer]
        .start("0.0.0.0", 8080, settings = ServerSettings(system))
    }
  }
}
