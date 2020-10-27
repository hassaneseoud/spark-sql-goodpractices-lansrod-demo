package com.hs.pipeline.demo.test.scalaTestExamples

import org.scalatest.FunSuite

class FunSuiteExample extends FunSuite{
  val listEmpty = List.empty
  val a = 1

  test("empty list should have size 0"){
    assert (listEmpty.size == 0, "empty list should have size 0")
    assert( a > 0 )
    assertDoesNotCompile ( "val a :String = 1" )
  }

  test("perform some tests on a list ") {
    val list = List (1,2,3,4,5,6,7,8)
    assert(list.contains(3))
    assertThrows[IndexOutOfBoundsException] {
      listEmpty(10)
    }
  }

}
