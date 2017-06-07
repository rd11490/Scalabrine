package com.rrdinsights.models

private[models] object Utils {
  def transformToT[T](value: Any): Option[T] =
   Option(value).map(_.asInstanceOf[T])

  def transformToString(value: Any): Option[String] = {
    val transformed = transformToT[String](value).map(_.trim)
    if (transformed.forall(_.isEmpty)) None else transformed
  }

  def transformToInt(value: Any): Option[Int] =
    transformToT[BigInt](value).map(_.intValue())

  def transformToDouble(value: Any): Option[Double] =
    transformToT[BigDecimal](value).map(_.doubleValue())

  def convertMinutesToDouble(time: String): Double ={
    val splitTime = time.split(":")
    val min = splitTime(0).toDouble
    val sec = splitTime(1).toDouble/60.0
    min+sec
  }
}
