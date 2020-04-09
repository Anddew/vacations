package com.evolutiongaming.vacations.employee

import java.util.UUID

import com.evolutiongaming.vacations.department.Department


final case class Employee(id: UUID, firstName: String, lastName: String, department: Department)
