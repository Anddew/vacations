package com.evolutiongaming.vacations

import java.util.UUID

import cats.effect.IO


trait Service[A] {

  def getAll(): IO[List[A]]

  def get(id: UUID): IO[Option[A]]

  def add(entity: A): IO[Unit]

  def remove(id: UUID): IO[Unit]

  def update(entity: A): IO[Unit]

}
