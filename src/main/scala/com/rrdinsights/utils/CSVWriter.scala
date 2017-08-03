package com.rrdinsights.utils

import java.io.{FileWriter, PrintWriter}

object CSVWriter {

  final implicit class CSVWriter[T <: Product](seq: TraversableOnce[T]) {

    def write(filePath: String): Unit = {
      val writer = new PrintWriter(new FileWriter(filePath))
      seq.map(v => {
        v.productIterator.map(c => Option(c) match {
          case Some(x) => convertToString(x)
          case None => ""
        }).mkString(",")
      }).foreach(writer.println)

      writer.close()
    }
  }

  private def convertToString(s: Any): String =
    if (s.isInstanceOf[String]) {
      "\"" + s + "\""
    } else {
      s.toString
    }

}
