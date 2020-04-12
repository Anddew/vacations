package com.evolutiongaming.vacations.vacation

import java.util.UUID

import cats.data.EitherT
import cats.effect.IO
import com.evolutiongaming.vacations.Service
import io.circe.generic.auto._
import io.circe.generic.semiauto._
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.io._
import org.http4s._
import org.http4s.circe._
import cats.implicits._

import scala.util.{Failure, Success, Try}


class VacationRoutes(private val vacationService: Service[Vacation]) {

  val vacationRoutes: HttpRoutes[IO] = HttpRoutes.of[IO] {

    case GET -> Root / "vacations" =>
      vacationService.getAll() *> Ok()

    case GET -> Root / "vacation" / UUIDVar(uuid) =>
      vacationService.get(uuid).flatMap {
        case Some(vacation) => Ok(s"vacation $vacation")
        case None           => Ok("No such vacation.")
      }

    case req @ PUT -> Root / "vacation" =>
      req.as[String].flatMap(vacation =>
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
      req.as[String].flatMap { vacation =>
        Ok(s"Update vacation '$vacation'")
      }

  }

}

object VacationRoutes {

  def apply(vacationService: VacationService): VacationRoutes = new VacationRoutes(vacationService)

}
