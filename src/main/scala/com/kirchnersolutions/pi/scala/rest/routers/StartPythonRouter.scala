package com.kirchnersolutions.pi.scala.rest.routers

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
import akka.http.scaladsl.server.directives.BasicDirectives.provide
import akka.http.scaladsl.server.directives.HeaderDirectives
import com.kirchnersolutions.pi.scala.rest.services.ProcessService.runPythonMain
import com.kirchnersolutions.pi.scala.rest.traits.{
  Auth,
  ConfigValues,
  ExtractToken
}

import scala.language.postfixOps
import scala.concurrent.ExecutionContext

trait StartPythonRouter
    extends ConfigValues
    with HeaderDirectives
    with ExtractToken {

  def runPythonRoute(implicit ec: ExecutionContext,
                     ac: ActorSystem,
                     device: Auth) =
    (headerValue(extractToken) | provide("null")) { value =>
      pathPrefix("start") {

        concat {
          pathEnd {
            complete("Invalid path")
          }
          path("python") {
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
