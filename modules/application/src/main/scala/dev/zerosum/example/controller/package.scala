package dev.zerosum.example

import wvlet.airframe._

package object controller {

  def createControllerDesign: Design = {
    newDesign
      .bind[GreetingController]
      .toSingleton
  }
}
