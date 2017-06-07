package com.rrdinsights.models

import com.rrdinsights.TestSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner


@RunWith(classOf[JUnitRunner])
class UtilsTest extends TestSpec {
  test("time to double") {
    val testTime1 = "240:00"
    val testTime2 = "12:06"
    val testTime3 = "00:06"

    assert(Utils.convertMinutesToDouble(testTime1) === 240.0)
    assert(Utils.convertMinutesToDouble(testTime2) === 12.1)
    assert(Utils.convertMinutesToDouble(testTime3) === 0.1)
  }
}
