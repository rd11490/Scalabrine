package com.rrdinsights.scalabrine.utils

import java.io.{FileWriter, PrintWriter}

object CSVWriter {

  final implicit class CSVWriter[T <: Product](seq: Iterable[T]) {

    def write(filePath: String)(implicit m: Manifest[T]): Unit = {
      val writer = new PrintWriter(new FileWriter(filePath))
      val fieldNames = implicitly[Manifest[T]].runtimeClass.getDeclaredFields.map(_.getName).mkString(",")
      writer.println(fieldNames)
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
