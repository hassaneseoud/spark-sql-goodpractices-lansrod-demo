package com.hs.pipeline.demo.test.sparkTestingExamples

import com.holdenkarau.spark.testing.StreamingSuiteBase
import org.apache.spark.streaming.dstream.DStream
import org.scalatest.FunSuite

class streamingTestExample extends FunSuite with StreamingSuiteBase {
  def convert (dstream : DStream[String]) = {
    dstream.flatMap(_.split(" "))
  }

  test (" test compareRDD") {
    val input = List (List("a"),List("a b"),List("c"))
    val expected = List (List("a"), List ("a","b"), List ("c"))
// def testOperation[U, V](input : scala.Seq[scala.Seq[U]],
      // operation : scala.Function1[org.apache.spark.streaming.dstream.DStream[U], org.apache.spark.streaming.dstream.DStream[V]],
      // expectedOutput : scala.Seq[scala.Seq[V]])(implicit evidence$2 : scala.reflect.ClassTag[U], evidence$3 : scala.reflect.ClassTag[V], equality : org.scalactic.Equality[V]) : scala.Unit = { /* compiled code */ }


    testOperation(input,convert _ , expected)
  }
}
