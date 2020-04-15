package com.evolutiongaming.vacations.vacation

import java.time.{Instant, LocalDate}
import java.util.{Date, UUID}

import cats.effect.IO
import cats.implicits._
import cats.instances._
import cats.syntax._
import com.evolutiongaming.vacations.Db
import io.circe._
import doobie._
import doobie.implicits._
import doobie.postgres._
import doobie.postgres.syntax._
import doobie.postgres.implicits._
import doobie.util.meta.Meta
import VacationRepository._


class VacationRepository(private val db: Db) {

  import doobie.implicits.legacy.localdate.JavaTimeLocalDateMeta

  def readAll(): IO[List[Vacation]] = {
    val queryVacations = Fragment.const(findVacationsSql)
    db.transactor.use { xa =>
      queryVacations.query[Vacation]
        .to[List]
        .transact(xa)
    }
  }

  def read(id: UUID): IO[Option[Vacation]] = {
    val queryVacation = Fragment.const(findVacationsSql) ++ Fragments.whereAnd(fr"vac.id=$id")
    db.transactor.use { xa =>
      queryVacation.query[Vacation]
        .option
        .transact(xa)
    }
  }

  def create(vacation: CreateVacation): IO[Unit] = {
    val createVacation = Fragment.const(createVacationSql) ++ Fragments.parentheses(Fragments.values(vacation))
    db.transactor.use { xa =>
      createVacation.update
        .withUniqueGeneratedKeys("id")
        .transact(xa)
    }
  }

  def update(entity: Vacation): IO[Unit] = ???
  def delete(id: UUID): IO[Unit] = ???

}

object VacationRepository {

  private val findVacationsSql: String =
    """SELECT
      |vac.id,
      |emp.id AS emp_id,
      |emp.firstName AS emp_firstName,
      |emp.lastName AS emp_lastName,
      |emp_dept.id AS emp_deptId,
      |emp_dept.name AS emp_deptName,
      |appr.id AS appr_id,
      |appr.firstName AS appr_firstName,
      |appr.lastName AS appr_lastName,
      |appr_dept.id AS appr_deptId,
      |appr_dept.name AS appr_deptName,
      |vac.start,
      |vac.until
      |FROM vacations vac
      |INNER JOIN employees emp ON vac.employeeId = emp.id
      |INNER JOIN departments emp_dept ON emp.departmentId = emp_dept.id
      |INNER JOIN employees appr ON vac.approverId = appr.id
      |INNER JOIN departments appr_dept ON appr.departmentId = appr_dept.id
      |""".stripMargin

  private val createVacationSql: String =
    "INSERT INTO vacations (employeeId, approverId, start, until) VALUES"


  def apply(db: Db): VacationRepository = new VacationRepository(db)

}
