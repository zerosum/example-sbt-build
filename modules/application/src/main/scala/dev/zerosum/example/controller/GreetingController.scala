package dev.zerosum.example.controller

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

class GreetingController() extends Controller {

  lazy val route: Route = path("") {
    get {
      complete(
        HttpEntity(ContentTypes.`text/plain(UTF-8)`, "Hello")
      )
    }
  }
}
