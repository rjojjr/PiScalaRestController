package com.kirchnersolutions.pi.scala.rest.routers

import akka.http.scaladsl.server.Directives.{as, complete, entity, path, post}
import com.kirchnersolutions.pi.scala.rest.traits.ConfigValues

import scala.concurrent.ExecutionContext

trait TempRouter extends ConfigValues{
  def tempRoute(implicit ec: ExecutionContextin) =
    path(temp_endpoint) {
      post {
          val res = logon(logonForm)
          complete(runCommand)
        }
      }
    }*/
}
