package com.hs.pipeline.demo.transformations

import com.hs.pipeline.demo.schema.{Clients, Country}
import org.apache.spark.sql.DataFrame

object ContractTransformation {

  def createBv(clientDf: DataFrame, countryDf: DataFrame)(contractDf: DataFrame): DataFrame =
  {
    val contractWithClientCols: DataFrame = Clients.joinWithClient(clientDf)(contractDf)

    val contractWithCountryCols: DataFrame = Country.joinWithCountry(countryDf)(contractWithClientCols)

    contractWithCountryCols
  }

}
