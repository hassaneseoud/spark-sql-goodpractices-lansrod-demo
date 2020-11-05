package com.hs.pipeline.demo.test.transformations

import com.hs.pipeline.demo.test.LsTestingBase
import com.hs.pipeline.demo.schema.Country.joinWithCountry
import com.hs.pipeline.demo.schema.Clients.joinWithClient
import com.hs.pipeline.demo.schema.{BusinessView, Clients, Contract, Country}
import com.hs.pipeline.demo.transformations.ContractTransformation
import com.hs.pipeline.demo.transformations.ContractTransformation.createBv
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.types.{StructField, _}

class ContractJoinsTests extends LsTestingBase{

  test("joinWithCountryTest"){

// loading from test/resources
    val countryDf = loadTestResourcesCSV("/joinWithCountryTest/Country.csv")
    val contractDf = loadTestResourcesCSV("/joinWithCountryTest/Contract.csv")
    val resultDf = loadTestResourcesCSV("/joinWithCountryTest/Result.csv")

  // output of the joinWithCountry
   val joinResultDf = joinWithCountry (countryDf)(contractDf)

    // def assertDataFrameEquals(expected : org.apache.spark.sql.DataFrame, result : org.apache.spark.sql.DataFrame)
    assertDataFrameEquals(resultDf, joinResultDf)
  }

  test("joinWithClientTest"){

    // loading from test/resources
    val clientDf = loadTestResourcesCSV("/joinWithClientTest/Client.csv")
    val contractDf = loadTestResourcesCSV("/joinWithClientTest/Contract.csv")
    val resultDf = loadTestResourcesCSV("/joinWithClientTest/Result.csv")

    // output of the joinWithClient
    val joinResultDf = joinWithClient (clientDf)(contractDf)
    assertDataFrameEquals(resultDf, joinResultDf)
  }

 test("calculateParticipation"){

   val resultSchema = StructType(Array(
     StructField(Contract.Id, StringType, true),
     StructField(Contract.Entity, StringType, true),
     StructField(Contract.Amount, DoubleType, true),
     StructField(BusinessView.Participation, DoubleType, true)
   ))


   val contractSchema = StructType(Array(
     StructField(Contract.Id, StringType, true),
     StructField(Contract.Entity, StringType, true),
     StructField(Contract.Amount, DoubleType, true)
   ))
   val contractDf = loadTestResourcesCSV("/calculateParticipation/Contract.csv", contractSchema)
   val resultDf = loadTestResourcesCSV("/calculateParticipation/Result.csv", resultSchema).orderBy(Contract.Id)
   assertDataFrameEquals(resultDf, ContractTransformation.calculateParticipation(contractDf).orderBy(Contract.Id))


 }


  test("ContractCreateBvTest"){

    val resultSchema = StructType(Array(
      StructField(Contract.Country, StringType, true),
      StructField(Contract.ClientId, StringType, true),
      StructField(Contract.Id, StringType, true),
      StructField(Contract.Entity, StringType, true),
      StructField(Contract.Amount, DoubleType, true),
      StructField(Clients.DoB, StringType, true),
      StructField(Country.Name, StringType, true),
      StructField(BusinessView.Participation, DoubleType, true)


    ))


    val contractSchema = StructType(Array(
      StructField(Contract.Id, StringType, true),
      StructField(Contract.ClientId, StringType, true),
      StructField(Contract.Country, StringType, true),
      StructField(Contract.Entity, StringType, true),
      StructField(Contract.Amount, DoubleType, true)
    ))


    val clientSchema = StructType(Array(
      StructField(Clients.ClientId, StringType, true),
      StructField(Clients.DoB, StringType, true)

    ))

    val countrySchema = StructType(Array(
      StructField(Country.CountryCode, StringType, true),
      StructField(Country.Name, StringType, true)

    ))
    // loading from test/resources
    val clientDf = loadTestResourcesCSV("/createBvTest/Client.csv",clientSchema)
    val contractDf = loadTestResourcesCSV("/createBvTest/Contract.csv",contractSchema)
    val countryDf = loadTestResourcesCSV("/createBvTest/Country.csv",countrySchema)
    val resultDf = loadTestResourcesCSV("/createBvTest/Result.csv",resultSchema).orderBy(Contract.Id)

    // output of the createBv

    val createBvResultDf =createBv(clientDf, countryDf)(contractDf).orderBy(Contract.Id)
    createBvResultDf.show()
    // def assertDataFrameEquals(expected : org.apache.spark.sql.DataFrame, result : org.apache.spark.sql.DataFrame)
    assertDataFrameEquals(resultDf, createBvResultDf)
  }

}
