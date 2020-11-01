package com.hs.pipeline.demo

import org.apache.spark.sql.{DataFrame, SparkSession}

object Load {

  val CsvDefaultDelimiter = ";"

  def loadCsvToDataframe(path: String)(implicit spark: SparkSession): DataFrame = {
    spark.read.option("header", "true").option("delimiter", CsvDefaultDelimiter).csv(path)
  }

  def selectAndRenameColumns(cols: Seq[String], renameMapping: Map[String, String] = Map()): DataFrame => DataFrame =
    df => {
    val selected: DataFrame = df.select(cols.head, cols.tail:_*)
    renameMapping.foldLeft(selected)((acc, mapping) => acc.withColumnRenamed(mapping._1, mapping._2))
  }

}
