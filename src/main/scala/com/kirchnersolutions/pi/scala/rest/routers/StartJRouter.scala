package com.kirchnersolutions.pi.scala.rest.routers

import java.awt.GraphicsDevice

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpHeader
import akka.http.scaladsl.server.Directives.{as, complete, entity, path, post}
import akka.http.scaladsl.server.directives.HeaderDirectives
import akka.http.scaladsl.server.directives.BasicDirectives._
import com.kirchnersolutions.pi.scala.rest.traits.ConfigValues
import com.kirchnersolutions.pi.scala.rest.services.ProcessService._
import com.kirchnersolutions.pi.scala.rest.traits.Auth

import scala.concurrent.ExecutionContext

trait StartJRouter extends ConfigValues with HeaderDirectives {
  val tokenValue = "token" toLowerCase

  def extractToken: HttpHeader => Option[String] = {
    case HttpHeader(`tokenValue`, value) => Some(value)
    case _                               => None
  }

  def runJRoute(implicit ec: ExecutionContext, ac: ActorSystem, device: Auth) =
    (headerValue(extractToken) | provide("null")) { value =>
      path(temp_endpoint) {
        post {
          if (device.validateToken(value)) {
            complete(runPiTemp())
          }
          complete("invalid token")
        }
      }
    }

}
