package com.hs.pipeline.demo.test.scalaTestExamples

import org.scalatest.FlatSpec

class FlatSpecExample extends FlatSpec{

  val listEmpty = List.empty
  val a = 1

  "listEmpty " should "have length 0" in {
    assert (listEmpty.size == 0, "empty list should have size 0")
    assert( a > 0 )
    assertDoesNotCompile ( "val a :String = 1" )
  }

  ignore should "throw exception when checked for element" in {
    assertThrows[IndexOutOfBoundsException] {
      listEmpty(2)
    }
  }
}
