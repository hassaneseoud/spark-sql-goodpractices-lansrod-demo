package com.hs.pipeline.demo.configuration

import com.typesafe.config.{Config, ConfigFactory}

object SquadContext {

  val squadConf: Config = {
    ConfigFactory.invalidateCaches()
    ConfigFactory.load()
  }

  val clientFilePath: String = squadConf.getString("clientFilePath")
}
