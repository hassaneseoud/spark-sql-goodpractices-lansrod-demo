package com.hs.pipeline.demo.test.scalaTestExamples

import org.scalatest.FunSuite

class AssertionsExamples extends  FunSuite {

   test ("Assertions Examples") {
     val a = 1
     val b = 2
     val c = 3
     val d = 4

     val list = List(a,b,c,d)

     //assert(a == b)
     assert ( a== b || c<d)
     assert(list.exists(el => el < 10))
     //assert(a == 10, "not equal val = "+ a)

     assertResult(1){b-a}

   }

  test("fail or succeed examples") {
  //  fail("I want it to fail")
   succeed
  }

  test("Exceptions examples") {
    val str = "dhekra"
    assertThrows[IndexOutOfBoundsException]{
      str.charAt(-1)
    }
    val message = intercept[IndexOutOfBoundsException] {
      str.charAt(-1)
    }
    println("*******************************" + message.getMessage)
  }

}
