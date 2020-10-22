package com.hs.pipeline.demo.configuration

import com.typesafe.config.{Config, ConfigFactory}

object LsContext {

  val LsConf: Config = {
    ConfigFactory.invalidateCaches()
    ConfigFactory.load()
  }

  val ContractFilePath: String = LsConf.getString("contractFilePath")
  val CountryFilePath: String = LsConf.getString("countryFilePath")
  val ClientFilePath: String = LsConf.getString("clientFilePath")
  val ResultFilePath: String = LsConf.getString("resultFilePath")
  val SparkMaster = LsConf.getString("sparkMaster")
  val SparkAppName = LsConf.getString("sparkAppName")

}
