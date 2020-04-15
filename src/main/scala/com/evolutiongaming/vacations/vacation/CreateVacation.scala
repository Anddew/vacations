package com.evolutiongaming.vacations.vacation

import java.util.UUID


final case class CreateVacation(employeeId: UUID, approverId: UUID, period: Period)
