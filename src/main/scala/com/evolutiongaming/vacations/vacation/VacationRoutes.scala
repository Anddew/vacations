package com.evolutiongaming.vacations.vacation

import java.util.UUID

import cats.effect.IO
import com.evolutiongaming.vacations.employee.Employee
import io.circe.generic.auto._
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.io._

import scala.util.{Failure, Success, Try}

class VacationRoutes {

  val vacationRoutes = HttpRoutes.of[IO] {

    case GET -> Root / "vacation" / UUIDVar(uuid) =>
      Ok(s"Find vacation $uuid")

    case req @ PUT -> Root / "vacation" =>
      req.as[Vacation].flatMap(vacation =>
        Ok(s"Add vacation '$vacation'")
      )

    case req @ DELETE -> Root / "vacation" =>
      req.as[String].flatMap { uuid =>
        Try(UUID.fromString(uuid)) match {
          case Success(uuid) => Ok(s"Delete vacation '$uuid'")
          case Failure(_)    => BadRequest(s"Invalid uuid - '$uuid'")
        }
      }

    case req @ POST -> Root / "vacation" =>
      req.as[Vacation].flatMap { vacation =>
        Ok(s"Update vacation '$vacation'")
      }

  }

  val employeeRoutes = HttpRoutes.of[IO] {
    case GET -> Root / "employee" / UUIDVar(uuid) =>
      Ok(s"Find employee $uuid")

    case req @ PUT -> Root / "employee" =>
      req.as[Employee].flatMap(vacation =>
        Ok(s"Add employee '$vacation'")
      )

    case req @ DELETE -> Root / "employee" =>
      req.as[String].flatMap { uuid =>
        Try(UUID.fromString(uuid)) match {
          case Success(uuid) => Ok(s"Delete employee '$uuid'")
          case Failure(_)    => BadRequest(s"Invalid uuid - '$uuid'")
        }
      }

    case req @ POST -> Root / "employee" =>
      req.as[Employee].flatMap { vacation =>
        Ok(s"Update employee '$vacation'")
      }
  }

}
