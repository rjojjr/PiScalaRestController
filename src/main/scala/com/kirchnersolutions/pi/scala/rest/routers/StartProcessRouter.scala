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
  post
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

trait StartProcessRouter
    extends ConfigValues
    with HeaderDirectives
    with ExtractToken {

  def runRoutes(implicit ec: ExecutionContext, ac: ActorSystem, device: Auth) =
    (headerValue(extractToken) | provide("null")) { value =>
      pathPrefix("start") {

        concat {
          pathEnd {
            complete("Invalid path")
          }
          path("pitemp") {
            post {
              if (device.validateToken(value)) {
                complete(runPiTemp())
              } else {
                complete("invalid token")
              }
            }
          }
          path("dht") {
            post {
              if (device.validateToken(value)) {
                complete(runPythonMain())
              } else {
                complete("invalid token")
              }
            }
          }
        }
      }
    }

}
