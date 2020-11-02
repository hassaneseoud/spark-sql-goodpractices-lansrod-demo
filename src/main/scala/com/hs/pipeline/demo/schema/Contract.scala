package com.hs.pipeline.demo.schema

import com.hs.pipeline.demo.Load._
import com.hs.pipeline.demo.configuration.LsContext
import org.apache.spark.sql.functions.sum
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._
object Contract {

  val Id = "id"
  val ClientId = "client_id"
  val Type = "type"
  val Amount = "amount"
  val Country = "country_code"
  val Entity = "entity_id"
  val OrderTime = "time"
  val BrokerId = "broker"
  val participation = "participation"

  val selectedCols = Seq(
      Id,
      ClientId,
      Type,
      Amount,
      Country,
      Entity,
      OrderTime,
    participation
  )

  //renamed cols
  val ContratType = "contract_type"


  val renameMapping = Map[String, String]{
    Type -> ContratType
  }

  def loadContractDf(date: String)(implicit spark: SparkSession): DataFrame = {
    val df = loadCsvToDataframe(LsContext.ContractFilePath + "/" + date)
    selectAndRenameColumns(selectedCols, renameMapping)(df)
  }

  def gitcalculateParticipation (contractDf: DataFrame): DataFrame = {

     contractDf.groupBy("Entity").(Amount.divide (sum("Amount"))).as("participation")

  }}
