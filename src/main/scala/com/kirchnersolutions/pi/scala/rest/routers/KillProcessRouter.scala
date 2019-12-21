package com.kirchnersolutions.pi.scala.rest.routers

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives.{
  as,
  complete,
  concat,
  entity,
  path,
  pathEnd,
  pathPrefix,
  post,
  get
}
import akka.http.scaladsl.server.directives.HeaderDirectives
import akka.http.scaladsl.server.directives.BasicDirectives._
import com.kirchnersolutions.pi.scala.rest.traits.{
  Auth,
  ConfigValues,
  ExtractToken
}
import com.kirchnersolutions.pi.scala.rest.services.ProcessService._

import scala.language.postfixOps
import scala.concurrent.ExecutionContext

trait KillProcessRouter
    extends ConfigValues
    with HeaderDirectives
    with ExtractToken {

  def killRoutes(implicit ec: ExecutionContext, ac: ActorSystem, device: Auth) =
    (headerValue(extractToken) | provide("null")) { value =>
      pathPrefix("kill") {

        concat {
          pathEnd {
            complete("Invalid path")
          }
          path("pitemp") {
            get {
              if (device.validateToken(value)) {
                complete(killProcess("pitemp", 2))
              } else {
                complete("invalid token")
              }
            }
          }
          path("dht") {
            get {
              if (device.validateToken(value)) {
                complete(killProcess("main.py", 2))
              } else {
                complete("invalid token")
              }
            }
          }
        }
      }
    }

}
