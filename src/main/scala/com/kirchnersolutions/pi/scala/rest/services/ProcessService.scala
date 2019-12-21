package com.kirchnersolutions.pi.scala.rest.services

import java.io.{BufferedReader, InputStreamReader}
import java.lang.Process
import java.lang.ProcessBuilder
import java.util.Scanner

import com.kirchnersolutions.pi.scala.rest.models.PsLine

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

  def killProcess(name: String, killCode: Int): String = {
    try {
      val pid = getPID(name)
      if (pid > 0){
        val p = Runtime.getRuntime.exec("kill -" + killCode + " " + pid)
        Thread.sleep(150)
        if(getPID(name) > 0){
          return "killed"
        }else{
          return "failed to kill"
        }
      }else{
        return "not running"
      }
    } catch {
      case err: Exception => "failed"
    }
  }

  def getPID(process: String): Int = {
    getProcesses().foreach(line => {
      if(line.command.toLowerCase.contains(process.toLowerCase)){
        return line.pid
      }
    })
    return 0
  }

  def getPsLine(process: String): PsLine = {
    getProcesses().foreach(line => {
      if(line.command.toLowerCase.contains(process.toLowerCase)){
        return line
      }
    })
    return new PsLine("notfound", 0, 0, 0, 0, 0, "",, "", "", "", "")
  }

  def getProcesses(): Seq[PsLine] = {
    val lines: Seq[PsLine] = new [PsLine]
    try {
      val p = Runtime.getRuntime.exec("ps -aux")
      val input = new BufferedReader(new InputStreamReader(p.getInputStream))
      val in = new Scanner(input)
      var count: Int = 0
      var user: String = ""
      var pid: Int = 0
      var cpu: Double = 0.0
      var mem = 0.0
      var vsz = 0
      var rss = 0
      var tty = "?"
      var stat = ""
      var start = ""
      var time = ""
      var command = ""
      while (in.hasNext()) {
        count match {
          case 0 => user = in.next()
          case 1 => pid = in.nextInt()
          case 2 => cpu = in.nextDouble()
          case 3 => mem = in.nextDouble()
          case 4 => vsz = in.nextInt()
          case 5 => rss = in.nextInt()
          case 6 => tty = in.next()
          case 7 => stat = in.next()
          case 8 => start = in.next()
          case 9 => time = in.next()
          case 10 => {
            command = in.next()
            lines ++ Seq(new PsLine(user, pid, cpu, mem, vsz, rss, tty, stat, start, time, command))
            count = -1
          }
        }
        count += 1
      }
      input.close()
      lines
    } catch {
      case err: Exception => Seq(new PsLine("failed", 0, 0, 0, 0, 0, "",, "", "", "", "")) ++ lines
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
