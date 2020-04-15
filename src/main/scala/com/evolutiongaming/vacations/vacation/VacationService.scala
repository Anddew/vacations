package com.evolutiongaming.vacations.vacation

import java.util.UUID

import cats.effect.IO


class VacationService(private val vacationRepository: VacationRepository) {

  def getAll(): IO[List[Vacation]] = {
    vacationRepository.readAll()
  }

  def get(id: UUID): IO[Option[Vacation]] = {
    vacationRepository.read(id)
  }

  def add(vacation: CreateVacation): IO[Unit] = {
    vacationRepository.create(vacation)
  }

  def remove(id: UUID): IO[Unit] = ???
  def update(entity: Vacation): IO[Unit] = ???

}


object VacationService {

  def apply(vacationRepository: VacationRepository): VacationService = new VacationService(vacationRepository)

}
