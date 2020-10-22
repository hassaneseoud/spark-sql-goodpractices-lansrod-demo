package com.hs.pipeline.demo.applications

import com.hs.pipeline.demo.schema.Contract.loadContractDf
import com.hs.pipeline.demo.schema.Clients.loadClientDf
import com.hs.pipeline.demo.schema.Country.loadCountryDf
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions.broadcast
import com.hs.pipeline.demo.configuration.LsContext
import com.hs.pipeline.demo.transformations.ContractTransformation

object Main {
  def main(args: Array[String]): Unit = {
    implicit val spark: SparkSession = SparkSession. builder. master(LsContext.SparkMaster) . appName(LsContext.SparkAppName).getOrCreate()
    spark.sparkContext.setLogLevel("ERROR")

    /**
     * loading the different files names for
     *  the contract.csv (with date)
     *  the clients for clients.csv
     *  the country for country.csv
     */
    val date = args(0)


    /**
     * creating the dataframes from reading from hdfs for clients, contracts and country information
     */
    val contractDf : DataFrame = loadContractDf(date)
    val clientDf : DataFrame = loadClientDf
    val countryDf: DataFrame = loadCountryDf

    /**
     * joining the contractDf with clientDf on client_id and dropping the name from clientDF
     * the result for the first join is joined with the countryDF on country_code
     */

      val bv: DataFrame = ContractTransformation.createBv(clientDf, countryDf)(contractDf)

    /**
     * writing the resulting joinedDf dataFrame in hdfs in parquet format
     */
    bv.write.mode("overwrite").parquet(LsContext.ResultFilePath + "finalData")

    spark.stop()
  }
}
