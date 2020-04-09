package com.evolutiongaming.vacations

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import com.evolutiongaming.vacations.vacation.VacationRoutes
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder

object VacationsApp extends IOApp {

  val host = "localhost"
  val port = 9000

  val HttpRoutes = new VacationRoutes

  val routes = HttpRoutes.vacationRoutes <+> HttpRoutes.employeeRoutes

  override def run(args: List[String]): IO[ExitCode] = {
    BlazeServerBuilder[IO]
      .bindHttp(port, host)
      .withHttpApp(routes.orNotFound)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
  }

}
