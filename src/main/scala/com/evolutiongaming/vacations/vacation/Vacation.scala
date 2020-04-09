package com.evolutiongaming.vacations.vacation

import java.util.{Date, UUID}

import cats.syntax.either._
import com.evolutiongaming.vacations.employee.Employee


sealed abstract class Vacation(id: UUID, employee: Employee, approver: Employee, fromDate: Date, toDate: Date)

object Vacation {

  def of(employee: Employee, approver: Employee, startDate: Date, finishDate: Date): Either[String, Vacation] = {
    if (finishDate.before(startDate)) (new Vacation(UUID.randomUUID(), employee, approver, startDate, finishDate) {}).asRight
    else s"Start date '$startDate' should be less or equal Finish date '$finishDate'".asLeft
  }

}
