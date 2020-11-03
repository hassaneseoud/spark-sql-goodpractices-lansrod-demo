package com.hs.pipeline.demo.schema

import com.hs.pipeline.demo.Load._
import com.hs.pipeline.demo.configuration.LsContext
import com.sun.jmx.mbeanserver.Util.cast
import org.apache.avro.generic.GenericData.StringType
import org.apache.spark
import org.apache.spark.sql.catalyst.dsl.expressions.{DslExpression, StringToAttributeConversionHelper}
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.{avg, col, sum}
import org.apache.spark.sql.{DataFrame, SparkSession}

import scala.Int.{int2double, int2float, int2long}
import scala.math.BigDecimal.int2bigDecimal
import scala.math.BigInt.int2bigInt
import scala.math.Integral.Implicits.infixIntegralOps
import scala.reflect.internal.util.TableDef.Column

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


  val renameMapping = Map[String, String] {
    Type -> ContratType
  }

  def loadContractDf(date: String)(implicit spark: SparkSession): DataFrame = {
    val df = loadCsvToDataframe(LsContext.ContractFilePath + "/" + date)
    selectAndRenameColumns(selectedCols, renameMapping)(df)
  }

  def calculateParticipation(contractDf: DataFrame): DataFrame = {
    contractDf.withColumn("sum", sum("Amount").over(Window.partitionBy("Entity")))
      .withColumn("p", col("Amount") / col("sum"))
      .withColumn("participation", col("p").cast("String"))
      .drop("p")
      .drop("sum")
      .drop("Amount")
      .orderBy("id")
  }
}


