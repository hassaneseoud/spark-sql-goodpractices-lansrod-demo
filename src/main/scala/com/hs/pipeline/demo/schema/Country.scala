package com.hs.pipeline.demo.schema

import com.hs.pipeline.demo.Load.loadCsvToDataframe
import com.hs.pipeline.demo.configuration.SquadContext
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * Object clients describes the clients.csv loaded in hdfs
 * it contains two columns:
 * @CountryCode  as the country code
 * @Name as the country name
 */
object Country {

  val CountryCode= "country_code"
  val Name = "name"

  val selectedCols = Seq(
    CountryCode,
    Name
  )

  /**
   * the below function loads the csv file from hdfs
   * @param fileName the filename
   * @param spark the sparkSession
   * @return the dataframe read from csv file
   */
  def loadCountryDf(fileName: String)(implicit spark: SparkSession): DataFrame = loadCsvToDataframe(SquadContext.ClientFilePath + "/" + fileName)
}
