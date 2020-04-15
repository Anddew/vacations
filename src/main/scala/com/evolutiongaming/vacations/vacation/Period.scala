package com.evolutiongaming.vacations.vacation

import java.time.LocalDate


final case class Period(start: LocalDate, until: LocalDate)

//object Period {
//
//  def of(start: LocalDate, until: LocalDate): Either[String, Period] = {
//    if (start.isAfter(until)) Left(s"Start date should not be after Until date: $start - $until")
//    else Right(Period(start, until))
//  }
//
//}
