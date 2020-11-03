package com.hs.pipeline.demo.test.transformations


import com.hs.pipeline.demo.transformations.ContractTransformation.calculateParticipation
import com.hs.pipeline.demo.test.LsTestingBase


class CalcParticipationTests extends LsTestingBase
{


  test("calcParticipationTest") {

    // loading from test/resources

    val contractDf = loadTestResourcesCSV("/calculateParticipation/Contract.csv")
    val resultDf = loadTestResourcesCSV("/calculateParticipation/result.csv")
    val resDf = calculateParticipation(contractDf)
    assertDataFrameEquals(resultDf, resDf)

  }
}