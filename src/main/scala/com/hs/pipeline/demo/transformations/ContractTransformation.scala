package com.hs.pipeline.demo.transformations

import com.hs.pipeline.demo.schema.BusinessView.{Participation, selectBvColumns}
import com.hs.pipeline.demo.schema.Contract.Amount
import com.hs.pipeline.demo.schema.{Clients, Contract, Country}
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.{col, sum}

object ContractTransformation {

  def createBv(clientDf: DataFrame, countryDf: DataFrame)(contractDf: DataFrame): DataFrame = {

    val transformations: List[DataFrame => DataFrame] = List(
      Clients.joinWithClient(clientDf),
      Country.joinWithCountry(countryDf),
      calculateParticipation
      //I commented the selection because we haven't contract_type and time columns
    // selectBvColumns
    )

      transformations.foldLeft(contractDf){
        (acc, transformation) => transformation(acc)
      }

  }

  def calculateParticipation: DataFrame => DataFrame = df =>
    {

      // I kept the sum col written in hard code because its use is local
      //it's just a temporary column dropped at the end of the function processing
      val windowing = Window.partitionBy(Contract.Entity)
      df.withColumn("sum", sum(col(Amount)) over windowing)
        .withColumn(Participation, col(Amount) / col("sum"))
        .drop("sum")
    }


}
