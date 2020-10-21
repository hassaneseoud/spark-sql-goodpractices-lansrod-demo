package com.hs.pipeline.demo.configuration

import com.typesafe.config.{Config, ConfigFactory}

object SquadContext {

  val squadConf: Config = {
    ConfigFactory.invalidateCaches()
    ConfigFactory.load()
  }

  val ClientFilePath: String = squadConf.getString("clientFilePath")
  val SparkMaster = squadConf.getString("sparkMaster")
  val SparkAppName = squadConf.getString("sparkAppName")

}
