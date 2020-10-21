package com.hs.pipeline.demo.schema

import com.hs.pipeline.demo.Load._
import com.hs.pipeline.demo.configuration.SquadContext
import org.apache.spark.sql.{DataFrame, SparkSession}

object Contract {

  val Id = "id"
  val ClientId = "client_id"
  val Type = "type"
  val Amount = "amount"
  val Country = "country_code"
  val Entity = "entity_id"
  val OrderTime = "time"
  val BrokerId = "broker"

  val selectedCols = Seq(
      Id,
      ClientId,
      Type,
      Amount,
      Country,
      Entity,
      OrderTime
  )

  //renamed cols
  val ContratType = "contract_type"


  val renameMapping = Map[String, String]{
    Type -> ContratType
  }

  def loadContractDf(date: String)(implicit spark: SparkSession): DataFrame = {
    val df = loadCsvToDataframe(SquadContext.ClientFilePath + "/" + date)
    selectAndRenameColumns(selectedCols, renameMapping)(df)
  }


}
