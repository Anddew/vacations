package com.evolutiongaming.vacations

import java.util.UUID

import com.evolutiongaming.vacations.employee.Employee
import com.evolutiongaming.vacations.vacation.{Period, Vacation}
import io.circe.generic.JsonCodec


sealed trait Message

trait InboundMessage extends Message
trait OutboundMessage extends Message

object Message {

  object VacationCommand {

    sealed trait VacationCommand extends Message

    @JsonCodec final case class CreateVacation(employeeId: UUID, approverId: UUID, period: Period) extends VacationCommand
    @JsonCodec final case class RemoveVacation(id: UUID) extends VacationCommand
    @JsonCodec final case class EditVacation(vacation: Vacation) extends VacationCommand

  }

  object EmployeeCommand {

    sealed trait EmployeeCommand extends Message

    @JsonCodec final case class CreateEmployee(firstName: String, lastName: String, departmentId: UUID) extends EmployeeCommand
    @JsonCodec final case class RemoveEmployee(id: UUID) extends EmployeeCommand
    @JsonCodec final case class EditEmployee(employee: Employee) extends EmployeeCommand

  }

  object DepartmentCommand {

    sealed trait DepartmentCommand extends Message

  }

  object LocationCommand {

    sealed trait LocationCommand extends Message

  }

  object UserCommand {

    sealed trait UserCommand extends Message

    @JsonCodec final case class RegisterUser(login: String, password: String) extends UserCommand
    @JsonCodec final case class Login(login: String, password: String) extends UserCommand
    @JsonCodec final case class TokenLogin(token: String) extends UserCommand
    @JsonCodec final case class Logout() extends UserCommand

  }

}
