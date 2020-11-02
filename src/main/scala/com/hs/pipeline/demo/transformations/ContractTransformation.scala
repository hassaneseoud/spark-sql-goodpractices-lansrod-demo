package com.hs.pipeline.demo.transformations

import com.hs.pipeline.demo.schema.{Clients, Country}
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object ContractTransformation {

  def createBv(clientDf: DataFrame, countryDf: DataFrame)(contractDf: DataFrame): DataFrame =
  {
    val contractWithClientCols: DataFrame = Clients.joinWithClient(clientDf)(contractDf)

    val contractWithCountryCols: DataFrame = Country.joinWithCountry(countryDf)(contractWithClientCols)

    contractWithCountryCols
  }

  def calculateParticipation(contractDf: DataFrame): DataFrame ={
    val windowing = Window.partitionBy("entity_id")
    contractDf.withColumn("amount", col("amount").cast(IntegerType))
              .withColumn("sum", sum(col("amount")) over windowing)
              .withColumn("percentage", col("amount")/col("sum")).drop("sum")
  }

}
