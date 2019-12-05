package com.kirchnersolutions.pi.scala.rest.traits

trait Auth {
  def validateToken(token: String): Boolean
  def getName(): String
}
