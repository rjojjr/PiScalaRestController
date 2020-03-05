package com.kirchnersolutions.pi.scala.rest.traits

import akka.http.scaladsl.model.HttpHeader

trait ExtractHeader {

  var key: String = (headerKey: String) => headerKey

  def extractHeader: HttpHeader => Option[String] = {

    case HttpHeader(key, value) => Some(value)
    case _                      => None
  }
}

trait ExtractToken {

  def extractToken: HttpHeader => Option[String] = {
    case HttpHeader("token", value) => Some(value)
    case _                          => None
  }

}
