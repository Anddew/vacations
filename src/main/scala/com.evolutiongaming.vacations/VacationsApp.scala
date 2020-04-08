package com.evolutiongaming.vacations

import cats.effect.{ExitCode, IO, IOApp}

object VacationsApp extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = for {
    _ <- IO(println("This is Vacations application."))
  } yield ExitCode.Success

}
