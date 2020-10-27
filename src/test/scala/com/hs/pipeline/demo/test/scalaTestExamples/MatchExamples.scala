package com.hs.pipeline.demo.test.scalaTestExamples

import org.scalatest.{FlatSpec,Matchers}

class MatchExamples extends FlatSpec with Matchers {

  " example " should "be " in {
    val a = 2
    a should equal (2) // you can change the equals
    a should === (2) //  same like equal but forces type equality
    a shouldEqual 2
    a should be (2) // does not care about equals
    a shouldBe 2

    Array(2,3) should equal(Array(2,3))

    Array(2,3) should have length 2
    Array(2,3) should have size 2

    a should be < 7
    a should be >= -5

    List.empty shouldBe empty
    List(1,2,3,4,5,6,7) should contain noneOf(12,10,51)
    List(1,2,3,4,5,6,7) should contain allOf (1,2,5)


    val str = "Amenallah"
    str  should startWith ("Amen")
    str  should include ("Amen")
  }

}
