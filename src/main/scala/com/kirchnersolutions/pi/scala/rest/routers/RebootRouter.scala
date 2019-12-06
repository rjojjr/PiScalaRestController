package com.kirchnersolutions.pi.scala.rest.routers

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpHeader
import akka.http.scaladsl.server.Directives.{complete, path, post}
import akka.http.scaladsl.server.directives.BasicDirectives.provide
import akka.http.scaladsl.server.directives.HeaderDirectives
import com.kirchnersolutions.pi.scala.rest.services.ProcessService.rebootPi
import com.kirchnersolutions.pi.scala.rest.traits.{Auth, ConfigValues}

import scala.concurrent.ExecutionContext

trait RebootRouter extends ConfigValues with HeaderDirectives {

  val tokenValue = "token" toLowerCase

  def extractToken: HttpHeader => Option[String] = {
    case HttpHeader(`tokenValue`, value) => Some(value)
    case _                               => None
  }

  def rebootRoute(implicit ec: ExecutionContext, ac: ActorSystem, device: Auth) =
    (headerValue(extractToken) | provide("null")) { value =>
      path(reboot_endpoint) {
        post {
          if (device.validateToken(value)) {
            complete(rebootPi())
          }
          complete("invalid token")
        }
      }
    }

}
