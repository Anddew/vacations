package com.evolutiongaming.vacations.model.user


sealed trait Role

object Role {

  final case object Employee extends Role
  final case object Manager extends Role
  final case object Admin extends Role

}
