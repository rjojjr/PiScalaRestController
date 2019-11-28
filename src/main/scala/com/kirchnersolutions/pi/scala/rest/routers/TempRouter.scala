package com.kirchnersolutions.pi.scala.rest.routers

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives.{as, complete, entity, path, post}

import com.kirchnersolutions.pi.scala.rest.traits.ConfigValues
import com.kirchnersolutions.pi.scala.rest.services.ProcessService.runCmd

import scala.concurrent.ExecutionContext

trait TempRouter extends ConfigValues{
  def tempRoute(implicit ec: ExecutionContext, ac: ActorSystem) =
    path(temp_endpoint) {
      post {
          val res = runCmd()
          complete(runCommand)
        }
      }
    }
}
