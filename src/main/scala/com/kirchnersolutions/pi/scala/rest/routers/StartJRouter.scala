package com.kirchnersolutions.pi.scala.rest.routers

import java.awt.GraphicsDevice

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives.{as, complete, entity, path, post}
import akka.http.scaladsl.server.directives.ParameterDirectives
import com.kirchnersolutions.pi.scala.rest.traits.ConfigValues
import com.kirchnersolutions.pi.scala.rest.services.ProcessService._
import com.kirchnersolutions.pi.scala.rest.traits.Auth

import scala.concurrent.ExecutionContext

trait StartJRouter extends ConfigValues with ParameterDirectives{
  def tempRoute(implicit ec: ExecutionContext, ac: ActorSystem, device: Auth) =
    path(temp_endpoint) {
      post {
        if (device.authToken())
          val res = runCmd()
          complete(runCommand)

        }
      }
    }
}
