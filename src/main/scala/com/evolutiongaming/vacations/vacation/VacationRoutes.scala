package com.evolutiongaming.vacations.vacation

import java.util.{Date, UUID}

import cats.data.EitherT
import cats.effect.IO
import io.circe._
import io.circe.generic.auto._
import io.circe.generic.semiauto._
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.io._
import org.http4s._
import org.http4s.circe._
import cats.implicits._
import com.evolutiongaming.vacations.employee.Employee
import io.circe.{Codec, Decoder, Encoder}

import scala.util.{Failure, Success, Try}


class VacationRoutes(private val vacationService: VacationService) {

  implicit val employeeCodec: Codec[Employee] = deriveCodec[Employee]
  implicit val periodCodec: Codec[Period] = deriveCodec[Period]
  implicit val vacationCodec: Codec[Vacation] = deriveCodec[Vacation]


  val vacationRoutes: HttpRoutes[IO] = HttpRoutes.of[IO] {

    case GET -> Root / "vacations" =>
      Ok(vacationService.getAll())

    case GET -> Root / "vacation" / UUIDVar(uuid) =>
      vacationService.get(uuid).flatMap {
        case Some(vacation) => Ok(vacation)
        case None           => Ok(s"No such vacation id - '$uuid'.")
      }

    case req @ POST -> Root / "vacation" =>
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

    case req @ POST -> Root / "vacation"/ "new" =>
      req.as[CreateVacation].flatMap { vacation =>
        vacationService.add(vacation) *> Ok()
      }

  }

}

object VacationRoutes {

  def apply(vacationService: VacationService): VacationRoutes = new VacationRoutes(vacationService)

}
