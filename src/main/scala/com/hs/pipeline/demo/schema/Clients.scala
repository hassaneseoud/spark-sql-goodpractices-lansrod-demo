package com.hs.pipeline.demo.schema

import com.hs.pipeline.demo.Load.loadCsvToDataframe
import com.hs.pipeline.demo.configuration.LsContext
import org.apache.spark.sql.functions.broadcast
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * Object clients describes the clients.csv loaded in hdfs
 * it contains three columns:
 * @ClientId  as the client id
 * @Name as the client name
 * @DoB as date of birth
 */
object Clients {

  val ClientId = "client_id"
  val Name = "name"
  val DoB = "DOB"

  val selectedCols = Seq(
      ClientId,
      DoB
    )

  /**
   * the below function loads the csv file from hdfs
   * @param fileName the filename
   * @param spark the sparkSession
   * @return the dataframe read from csv file
   */
  def loadClientDf(implicit spark: SparkSession): DataFrame =
    loadCsvToDataframe(LsContext.ClientFilePath)
      .select(selectedCols.head, selectedCols.tail: _*)

  def joinWithClient(clientDf: DataFrame)(contractDf: DataFrame): DataFrame =
    contractDf.join(broadcast(clientDf), Seq(ClientId),"leftouter" )

}
