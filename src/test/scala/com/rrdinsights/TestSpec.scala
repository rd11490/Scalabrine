package com.rrdinsights

import org.scalatest.{FunSuite, Matchers}
import org.scalatest.mockito.MockitoSugar
import org.scalactic.TypeCheckedTripleEquals

abstract class TestSpec extends FunSuite with TypeCheckedTripleEquals with Matchers with MockitoSugar {

}
