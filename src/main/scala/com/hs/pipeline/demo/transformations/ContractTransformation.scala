package com.hs.pipeline.demo.transformations

import com.hs.pipeline.demo.schema.{Clients, Country}
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.{col, sum}
import org.apache.spark.sql.types.{DoubleType, IntegerType}

object ContractTransformation {

  def createBv(clientDf: DataFrame, countryDf: DataFrame)(contractDf: DataFrame): DataFrame =
  {
    val contractWithClientCols: DataFrame = Clients.joinWithClient(clientDf)(contractDf)

    val contractWithCountryCols: DataFrame = Country.joinWithCountry(countryDf)(contractWithClientCols)

    contractWithCountryCols
  }

  def calParticipation(contractDf: DataFrame): DataFrame = {
    val sumAmount = contractDf.groupBy("entity_id").agg(sum(contractDf("amount").cast(IntegerType)) as "sum_amount")
    val interDf = contractDf.join(sumAmount,"entity_id")
    val resultDf = interDf.withColumn("participation",
      col("amount").cast(DoubleType) / col("sum_amount").cast(DoubleType)).drop("sum_amount")

    resultDf
  }

}
