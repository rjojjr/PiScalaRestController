package com.kirchnersolutions.pi.scala.rest.services

import java.lang.Process
import java.lang.ProcessBuilder

object ProcessService {

  def runCmd(args: String): String = {
    val process = new ProcessBuilder(args).start()
    process waitFor ()
    process getOutputStream () toString
  }

}
