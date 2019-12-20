package com.kirchnersolutions.pi.scala.rest.routers

import java.awt.GraphicsDevice

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpHeader
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
import com.kirchnersolutions.pi.scala.rest.traits.ConfigValues
import com.kirchnersolutions.pi.scala.rest.services.ProcessService._
import com.kirchnersolutions.pi.scala.rest.traits.Auth

import scala.language.postfixOps
import scala.concurrent.ExecutionContext

trait StartJRouter extends ConfigValues with HeaderDirectives {

  def extractToken: HttpHeader => Option[String] = {
    case HttpHeader("token", value) => Some(value)
    case _                          => None
  }

  def runJRoute(implicit ec: ExecutionContext, ac: ActorSystem, device: Auth) =
    (headerValue(extractToken) | provide("null")) { value =>
      pathPrefix("start") {

        concat {
          pathEnd {
            complete("Invalid path")
          }
          path("pitemp") {
            post {
              if (device.validateToken(value)) {
                complete(runPythonMain())
              }
              complete("invalid token")
            }
          }
        }
      }
    }

}
