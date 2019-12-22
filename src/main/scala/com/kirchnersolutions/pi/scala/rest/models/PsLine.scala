package com.kirchnersolutions.pi.scala.rest.models

case class PsLine(user: String,
                  pid: Int,
                  cpu: Double,
                  mem: Double,
                  vsz: Int,
                  rss: Int,
                  tty: String,
                  stat: String,
                  start: String,
                  time: String,
                  command: String)
object PsLine extends RestObject
