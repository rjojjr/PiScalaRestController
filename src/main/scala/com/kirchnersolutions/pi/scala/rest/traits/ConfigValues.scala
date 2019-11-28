package com.kirchnersolutions.pi.scala.rest.traits

import com.typesafe.config.ConfigFactory

trait ConfigValues {
  implicit val config = ConfigFactory.load()
  val temp_endpoint = config.getString("server.endpoint.get-temp")
  val run_command_endpoint = config.getString("server.endpoint.run-command")
}
