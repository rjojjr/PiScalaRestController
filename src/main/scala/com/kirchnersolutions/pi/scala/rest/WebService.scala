package com.kirchnersolutions.pi.scala.rest

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{ExceptionHandler, Route}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.kirchnersolutions.pi.scala.rest.routers.{
  GetProcessesRouter,
  KillProcessRouter,
  RebootRouter,
  StartProcessRouter
}
import com.kirchnersolutions.pi.scala.rest.traits.Auth
import com.typesafe.config.ConfigFactory

import scala.language.postfixOps
import scala.io.StdIn

object WebService {

  def main(args: Array[String]): Unit = {

    val ip = args(2)
    val port: Int = args(3) toInt

    implicit object Device extends Auth {

      private val name = args(0)
      private val token: java.lang.String = args(1)

      def getName(): String = {
        name
      }

      def validateToken(token: java.lang.String): Boolean = {
        if (this.token == token) {
          true
        } else {
          false
        }
      }
    }

    val config = ConfigFactory.load()
    val host = config.getString("http.host") // Gets the host and a port from the configuration
    //val port = config.getInt("http.port")

    implicit val system
      : ActorSystem = ActorSystem("actor-system") // ActorMaterializer requires an implicit ActorSystem
    implicit val executionContextExecutor = system.dispatcher // bindingFuture.map requires an implicit ExecutionContext

    implicit val materializer = ActorMaterializer() // bindAndHandle requires an implicit materializer
    object MainRouter$$
        extends StartProcessRouter
        with RebootRouter
        with KillProcessRouter
        with GetProcessesRouter {
      val routes = runDHTRoute ~ runPiTempRoute ~ rebootRoute ~ killDHTRoutes ~ killPiTempRoutes ~ getRoute
    }

    val errorHandler = ExceptionHandler {
      case exception => complete(StatusCodes.BadRequest, exception.toString)
    }
    def routes = handleExceptions(errorHandler) { MainRouter$$.routes }
    val bindingFuture = Http().bindAndHandle(routes, ip, port)

    //Comment last lines out to run ~reStart
    println(
      s"Server online at " + ip + ":" + port + "\nPress RETURN to stop..."
    )
    //StdIn.readLine() // let it run until user presses return
    //bindingFuture
    //.flatMap(_.unbind()) // trigger unbinding from the port
    //  .onComplete(_ => system.terminate()) // and shutdown when done*/

  }
}
