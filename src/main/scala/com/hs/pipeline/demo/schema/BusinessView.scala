package com.hs.pipeline.demo.schema

import com.hs.pipeline.demo.Load.selectAndRenameColumns
import com.hs.pipeline.demo.schema.Contract.{Amount, ClientId, ContratType, Entity, Id, OrderTime}
import org.apache.spark.sql.DataFrame

object BusinessView {

  //calculated new columns
  val Participation = "participation"

  val selectedColumns = Seq(
    Id,
    ClientId,
    ContratType,
    Amount,
    Entity,
    OrderTime,
    Clients.DoB,
    Country.Name,
    Participation
  )

  def selectBvColumns: DataFrame => DataFrame =
    df => selectAndRenameColumns(selectedColumns)(df)

}
