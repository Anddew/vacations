package com.evolutiongaming.vacations

import java.util.UUID

import com.evolutiongaming.vacations.employee.Employee
import com.evolutiongaming.vacations.vacation.{Period, Vacation}


sealed trait AppCommand

object AppCommand {

  object VacationCommand {

    sealed trait VacationCommand extends AppCommand

    final case class CreateVacation(employeeId: UUID, approverId: UUID, period: Period) extends VacationCommand
    final case class RemoveVacation(id: UUID) extends VacationCommand
    final case class EditVacation(vacation: Vacation) extends VacationCommand

  }

  object EmployeeCommand {

    sealed trait EmployeeCommand extends AppCommand

    final case class CreateEmployee(firstName: String, lastName: String, departmentId: UUID) extends EmployeeCommand
    final case class RemoveEmployee(id: UUID) extends EmployeeCommand
    final case class EditEmployee(employee: Employee) extends EmployeeCommand

  }

  object DepartmentCommand {

    sealed trait DepartmentCommand extends AppCommand

  }

  object LocationCommand {

    sealed trait LocationCommand extends AppCommand

  }

  object UserCommand {

    sealed trait UserCommand extends AppCommand

    final case class RegisterUser(login: String, password: String) extends UserCommand
    final case class Login(login: String, password: String) extends UserCommand
    final case class TokenLogin(token: String) extends UserCommand
    final case class Logout() extends UserCommand

  }

}
