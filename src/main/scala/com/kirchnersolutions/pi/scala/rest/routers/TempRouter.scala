package com.kirchnersolutions.pi.scala.rest.routers

import akka.http.scaladsl.server.Directives.{as, complete, entity, path, post}

import scala.concurrent.ExecutionContext

trait TempRouter {
  def loginRoute(implicit ec: ExecutionContextin) =
    path(PiCenterConstants.LOGIN_ENDPOINT) {
      post {
        entity(as[PiCenterConstants.LogonForm]) { logonForm =>
          val res = logon(logonForm)
          complete(res)
        }
      }
    }
}
