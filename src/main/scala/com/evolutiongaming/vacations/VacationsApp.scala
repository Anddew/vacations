package com.evolutiongaming.vacations

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import com.evolutiongaming.vacations.config.AppConfig
import com.evolutiongaming.vacations.vacation.{VacationRepository, VacationRoutes, VacationService}
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import com.evolutiongaming.vacations.dashboard.Dashboard.{routes => dashboardRoutes}
import com.typesafe.config.ConfigFactory


object VacationsApp extends IOApp {

  val config = ConfigFactory.load()
  val appConfig = AppConfig(config)

  val db = Db(contextShift)
  val vacationRepository = VacationRepository(db)
  val vacationService = VacationService(vacationRepository)
  val vacationRoutes = VacationRoutes(vacationService)

  val testRoute = HttpRoutes.of[IO] {
    case GET -> Root / "test" =>
      println("test")
      Ok("test")
  }

  val routes = testRoute <+> vacationRoutes.vacationRoutes <+> dashboardRoutes

  override def run(args: List[String]): IO[ExitCode] = {
    BlazeServerBuilder[IO]
      .bindHttp(appConfig.server.port, appConfig.server.host)
      .withHttpApp(routes.orNotFound)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
  }

}
