package com.evolutiongaming.vacations.vacation

import java.util.UUID

import cats.effect.{Bracket, IO}
import com.evolutiongaming.vacations.{Db, Repository}
import doobie.implicits._
import doobie._
import doobie.postgres._
import cats.implicits._
import cats.instances._
import cats.syntax._
import doobie.util.meta.Meta


class VacationRepository(private val db: Db) extends Repository[Vacation] {

  import db._

  override def readAll(): IO[List[Vacation]] = {
    val queryVacations = Fragment.const("SELECT id FROM vacations;")
    db.transactor.use { xa =>
      for {
        res <- queryVacations.query[String].to[List].transact(xa)
        _ <- IO(println(res))
        result <- IO(List.empty[Vacation])
      } yield result
    }
  }

  override def read(id: UUID): IO[Option[Vacation]] = {
    val queryVacation =
//      Fragment.const("SELECT id FROM vacations.vacations WHERE id = $id;")
          sql"SELECT id FROM vacations WHERE id = $id;"
    db.transactor.use { xa =>
      (for {
        res <- queryVacation.query[String].option.transact(xa)
        _ <- IO(println(res))
      } yield ()) *> IO(None)

    }
  }
  override def create(entity: Vacation): IO[Unit] = ???
  override def update(entity: Vacation): IO[Unit] = ???
  override def delete(id: UUID): IO[Unit] = ???

}

object VacationRepository {

  def apply(db: Db): VacationRepository = new VacationRepository(db)

}
