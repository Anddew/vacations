package com.evolutiongaming.vacations.vacation

import java.util.UUID

import com.evolutiongaming.vacations.employee.Employee


final case class Vacation(id: UUID, employee: Employee, approver: Employee, period: Period)
