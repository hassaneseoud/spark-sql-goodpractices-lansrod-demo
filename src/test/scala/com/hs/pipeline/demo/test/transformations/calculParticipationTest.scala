package com.hs.pipeline.demo.test.transformations


import com.hs.pipeline.demo.transformations.ContractTransformation.calculateParticipation
import com.hs.pipeline.demo.test.LsTestingBase
class calculParticipationTest extends LsTestingBase
{


  test("calculParticipationTest") {

    // loading from test/resources

    val contractDf = loadTestResourcesCSV("/calculateParticipation/Contract.csv")
    val resultDf = loadTestResourcesCSV("/calculateParticipation/Result.csv")
    val resDf = calculateParticipation(contractDf)
    assertDataFrameEquals(resultDf, resDf)

  }
}