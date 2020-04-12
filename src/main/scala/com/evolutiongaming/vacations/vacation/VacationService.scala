package com.evolutiongaming.vacations.vacation

import java.util.UUID

import cats.effect.IO
import com.evolutiongaming.vacations.{Repository, Service}


class VacationService(private val vacationRepository: Repository[Vacation]) extends Service[Vacation] {

  override def getAll(): IO[List[Vacation]] = {
    vacationRepository.readAll()
  }

  override def get(id: UUID): IO[Option[Vacation]] = {
    vacationRepository.read(id)
  }

  override def add(entity: Vacation): IO[Unit] = ???
  override def remove(id: UUID): IO[Unit] = ???
  override def update(entity: Vacation): IO[Unit] = ???

}


object VacationService {

  def apply(vacationRepository: Repository[Vacation]): VacationService = new VacationService(vacationRepository)

}
