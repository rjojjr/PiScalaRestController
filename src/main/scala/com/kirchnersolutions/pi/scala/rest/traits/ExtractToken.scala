package com.kirchnersolutions.pi.scala.rest.traits

import akka.http.scaladsl.model.HttpHeader

trait ExtractToken {

  def extractToken: HttpHeader => Option[String] = {
    case HttpHeader("token", value) => Some(value)
    case _                          => None
  }

}
