package com.kirchnersolutions.pi.scala.rest.services

import java.io.{BufferedReader, InputStreamReader}
import java.lang.Process
import java.lang.ProcessBuilder

object ProcessService {

  def runPiTemp(args: String): String = {
    try {
      var found = false
      var line = ""
      val p = Runtime.getRuntime.exec("ps -aux")
      val input = new BufferedReader(new InputStreamReader(p.getInputStream))
      while ((line = input.readLine) != null) {
        if (line.contains("java -jar pitemp")) {
          found = true
        } else found = false
      }
      input.close()
      if (found) {
        "running"
      } else {
        Runtime.getRuntime.exec("java -jar pitemp-2.0.03b.jar")
        "started"
      }
    } catch {
      case err: Exception => "failed"
    }
  }

}
