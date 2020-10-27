package com.hs.pipeline.demo.test.transformations

import com.hs.pipeline.demo.test.LsTestingBase
import com.hs.pipeline.demo.schema.Country.joinWithCountry
import com.hs.pipeline.demo.schema.Clients.joinWithClient
import com.hs.pipeline.demo.transformations.ContractTransformation.createBv

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

    // def assertDataFrameEquals(expected : org.apache.spark.sql.DataFrame, result : org.apache.spark.sql.DataFrame)
    assertDataFrameEquals(resultDf, joinResultDf)
  }


  test("ContractCreateBvTest"){

    // loading from test/resources
    val clientDf = loadTestResourcesCSV("/createBvTest/Client.csv")
    val contractDf = loadTestResourcesCSV("/createBvTest/Contract.csv")
    val countryDf = loadTestResourcesCSV("/createBvTest/Country.csv")
    val resultDf = loadTestResourcesCSV("/createBvTest/Result.csv")

    // output of the createBv
    val createBvResultDf = createBv(clientDf, countryDf)(contractDf)

    // def assertDataFrameEquals(expected : org.apache.spark.sql.DataFrame, result : org.apache.spark.sql.DataFrame)
    assertDataFrameEquals(resultDf, createBvResultDf)
  }

}
