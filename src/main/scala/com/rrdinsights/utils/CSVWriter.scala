package com.rrdinsights.utils

import java.io.{FileWriter, PrintWriter}

object CSVWriter {

  final implicit class CSVWriter[T <: Product](seq: TraversableOnce[T]) {

    def write(filePath: String): Unit = {
      val writer = new PrintWriter(new FileWriter(filePath))

    }

    /*def toCSV(prod: T): String = {
    }*/
  }

}
