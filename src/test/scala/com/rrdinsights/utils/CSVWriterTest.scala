package com.rrdinsights.utils

import com.rrdinsights.TestSpec
import java.{lang => jl}

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
final class CSVWriterTest extends TestSpec {

  ignore("write test") {

    import CSVWriter._

    val cases = Seq(
      TestClass("test1", 1, 1L, active = true),
      TestClass("test2", 2, 2L, active = true),
      TestClass("test3", 3, 3L, active = false)
    ).write("test.csv")
  }

  ignore("write test2") {

    import CSVWriter._

    val cases = Seq(
      TestClass2("test1", 1, 1.0),
      TestClass2("test2", 2, null),
      TestClass2("test3", null, null)
    ).write("test2.csv")
  }
}

private final case class TestClass(name: String, count: Int, count2: Long, active: Boolean)
private final case class TestClass2(name: String, score: jl.Integer, count2: jl.Double)