package com.evolutiongaming.vacations.model

import java.util.UUID


sealed abstract class Location private(country: String, city: String)

object Location {

  def of(country: String, city: String): Either[String, Location] = {
    if (country.length != 2) Left(s"County should consist of exactly 2 symbols. Current - '$country'.")
    else Right(new Location(country.toUpperCase, city) {})
  }

}

