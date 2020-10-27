package com.hs.pipeline.demo.test.scalaTestExamples

import org.scalatest.FlatSpec
import org.scalatest.BeforeAndAfter


class beforeAfterExample extends FlatSpec with BeforeAndAfter {

  val strBuilder = new StringBuilder
  var a = 1

  before{
    a= 2
    println("before a= "+ a)
    strBuilder.append("Kouki")
  }

  " strBuilder " should "be Kouki" in {

    println(" start of the test ")
    assert (strBuilder.toString === "Kouki")
    println(" end of the test : a = "+a)
    a = 5
  }

  after {
    strBuilder.clear()
    println(" the strBuilder is clear a= "+ a)
    a= 1
  }

}
