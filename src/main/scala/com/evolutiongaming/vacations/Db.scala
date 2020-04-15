package com.evolutiongaming.vacations

import java.time.{LocalDate, Year}
import java.util.UUID

import cats.effect.{Blocker, ContextShift, IO, Resource}
import doobie.util.meta.Meta
import doobie.util.transactor.Transactor


trait Db {

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
