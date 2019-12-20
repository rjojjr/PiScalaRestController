package com.kirchnersolutions.pi.scala.rest.services

import java.io.{BufferedReader, InputStreamReader}
import java.lang.Process
import java.lang.ProcessBuilder

object ProcessService {

  def runPiTemp(): String = {
    try {
      var found = false
      var line = ""
      val p = Runtime.getRuntime.exec("ps -aux")
      val input = new BufferedReader(new InputStreamReader(p.getInputStream))
      line = input.readLine
      while (line != null) {
        if (line.contains("java -jar pitemp")) {
          found = true
        }
        line = input.readLine
      }
      input.close()
      if (found) {
        "running"
      } else {
        Runtime.getRuntime.exec("bash startJ.sh")
        "started"
      }
    } catch {
      case err: Exception => "failed"
    }
  }

  def runPythonMain(): String = {
    try {
      var found = false
      var line = ""
      val p = Runtime.getRuntime.exec("ps -aux")
      val input = new BufferedReader(new InputStreamReader(p.getInputStream))
      line = input.readLine
      while (line != null) {
        if (line.contains("python main")) {
          found = true
        } else found = false
        line = input.readLine
      }
      input.close()
      if (found) {
        "running"
      } else {
        Runtime.getRuntime.exec("bash startPy.sh")
        "started"
      }
    } catch {
      case err: Exception => "failed"
    }
  }

  def rebootPi(): String = {
    try {
      Runtime.getRuntime.exec("sudo reboot")
      "rebooting"
    } catch {
      case err: Exception => "failed"
    }
  }

}
