package dev.zerosum.example.controller

import akka.http.scaladsl.server.Route

trait Controller {
  def route: Route
}
