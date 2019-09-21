package dev.zerosum.example.server

import akka.http.scaladsl.server.{Directives, Route}
import dev.zerosum.example.controller.{Controller, GreetingController}
import wvlet.airframe._

trait Routes extends Directives {

  private lazy val greetingController = bind[GreetingController]

  lazy val routes: Route = concatRoutes(
    greetingController,
  )

  private def concatRoutes(controllers: Controller*): Route =
    controllers.map(_.route).reduce(_ ~ _)
}
