package com.evolutiongaming.vacations

import java.util.UUID

import cats.effect.IO


trait Repository[A] {

  def readAll(): IO[List[A]]

  def read(id: UUID): IO[Option[A]]

  def create(entity: A): IO[Unit]

  def update(entity: A): IO[Unit]

  def delete(id: UUID): IO[Unit]

}
