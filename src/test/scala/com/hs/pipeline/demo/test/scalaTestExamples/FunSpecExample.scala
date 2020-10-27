package com.hs.pipeline.demo.test.scalaTestExamples

import org.scalatest.FunSpec


class FunSpecExample extends FunSpec{
  val listEmpty = List.empty
  describe("list") {
    describe(" when it is empty"){
     it ("should  have length 0" ) {
        assert (listEmpty.size == 0, "empty list should have size 0")
      }

    }
    it ("should throw exception ") {
      assertThrows[IndexOutOfBoundsException] {
        listEmpty(4)
      }
      assert(Array(1,2,3) ===  Array(1,2,3))
    }
  }
}
