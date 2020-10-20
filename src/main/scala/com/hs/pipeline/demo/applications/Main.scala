package com.hs.pipeline.demo.applications

import com.hs.pipeline.demo.schema.Contract.loadContractDf
import org.apache.spark.sql.{DataFrame, SparkSession}

object Main {
  def main(args: Array[String]): Unit = {
    implicit val spark: SparkSession = SparkSession.builder().getOrCreate()

    val date = args(0)

    val contractDf : DataFrame = loadContractDf(date)

    //transformations
    //T101...

    //publish on hive or on hdfs as parquet files

    spark.stop()

}}
