package com.evolutiongaming.vacations

import java.time.Year
import java.util.UUID

import cats.effect.{Blocker, ContextShift, IO, Resource}
import doobie.util.meta.Meta
import doobie.util.transactor.Transactor


trait Db {

  implicit val uuidMeta: Meta[UUID] = Meta[String].timap(UUID.fromString)(_.toString)
  implicit val yearMeta: Meta[Year] = Meta[Int].timap(Year.of)(_.getValue)

  val transactor: Resource[IO, Transactor[IO]]

}

object Db {

  def apply(implicit contextShift: ContextShift[IO]): Db = new Db {
    override val transactor: Resource[IO, Transactor[IO]] = for {
      be <- Blocker[IO]
    } yield {
      Transactor.fromDriverManager[IO](
        url = "jdbc:postgresql://localhost:5432/vacations",
        user = "postgres",
        pass = "postgrespwd",
        blocker = be,
        driver = "org.postgresql.Driver"
      )
    }
  }

}
