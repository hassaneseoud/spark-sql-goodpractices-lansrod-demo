package com.hs.pipeline.demo.test.sparkTestingExamples

import com.holdenkarau.spark.testing.{RDDComparisons, SharedSparkContext}
import org.apache.spark.rdd.RDD
import org.scalatest.FunSuite

class rddTestExamples extends FunSuite with SharedSparkContext with  RDDComparisons{

  def convert (rdd : RDD[String]) = {
    rdd.map(el=> el.split(" ").toList)
  }

  test (" test compareRDD") {
    val list1 = List ("a","a b","c")
    val list2 = List (List("a"), List ("a","b"), List ("c"))
    val rdd1 :RDD[List[String]]= convert(sc.parallelize(list1))
    val rdd2 :RDD[List[String]]= sc.parallelize(list2)

    //assert(None === compareRDD(rdd1,rdd2))
    //assertRDDEquals(rdd1,rdd2)

    assert (rdd1.collect.toList === rdd2.collect.toList)
  }
}
