package com.rrdinsights.scalabrine.models

import java.{lang => jl}

private[models] object Utils {
  def transformToT[T](value: Any): T =
    try {
      value.asInstanceOf[T]
    } catch {
      case e: Throwable =>
        println(value)
        println(e)
        throw e
    }

  def transformToString(value: Any): String = {
    val transformed = transformToT[String](value)
    if (transformed == null || transformed.trim.isEmpty) null else transformed.trim
  }

  def transformToInt(value: Any): jl.Integer = {
    try {
      val transformed = transformToT[BigInt](value)
      if (transformed != null) jl.Integer.valueOf(transformed.intValue()) else null
    } catch {
      case e: Throwable =>
        println(value)
        println(e)
        throw e
    }

  }

  def transformToDouble(value: Any): jl.Double = {
    val transformed = transformToT[BigDecimal](value)
    if (transformed != null) jl.Double.valueOf(transformed.doubleValue()) else null
  }


  def convertMinutesToDouble(time: String): jl.Double = {
    if (time != null && time.nonEmpty && time.contains(":")) {
      val splitTime = time.split(":")
      val min = splitTime(0).toDouble
      val sec = splitTime(1).toDouble / 60.0
      min + sec
    } else {
      null
    }
  }
}
