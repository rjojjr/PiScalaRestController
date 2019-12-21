package com.kirchnersolutions.pi.scala.rest.routers

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives.{
  as,
  complete,
  concat,
  entity,
  get,
  path,
  pathEnd,
  pathPrefix,
  post
}
import akka.http.scaladsl.server.directives.HeaderDirectives
import akka.http.scaladsl.server.directives.BasicDirectives._
import com.kirchnersolutions.pi.scala.rest.models.CmdRequest
import com.kirchnersolutions.pi.scala.rest.traits.{
  Auth,
  ConfigValues,
  ExtractToken
}
import com.kirchnersolutions.pi.scala.rest.services.ProcessService._

import scala.language.postfixOps
import scala.concurrent.ExecutionContext

trait GetProcessesRouter
    extends ConfigValues
    with HeaderDirectives
    with ExtractToken {

  def getRoute(implicit ec: ExecutionContext, ac: ActorSystem, device: Auth) =
    (headerValue(extractToken) | provide("null")) { value =>
      path("processes") {
        get {
          if (device.validateToken(value)) {
            complete(getProcesses())
          } else {
            complete("invalid token")
          }
        }
        post {
          entity(as[CmdRequest]) { getProcess =>
            if (device.validateToken(value)) {
              complete(getPsLine(getProcess.command))
            } else {
              complete("invalid token")
            }
          }
        }
      }
    }

}
