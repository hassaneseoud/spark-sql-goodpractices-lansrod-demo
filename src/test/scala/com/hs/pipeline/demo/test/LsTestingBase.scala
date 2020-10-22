package com.hs.pipeline.demo.test

import com.holdenkarau.spark.testing.{DataFrameSuiteBase, SharedSparkContext}
import org.apache.spark.sql.types.{StructField, StructType}
import org.apache.spark.sql.{DataFrame, DataFrameReader}
import org.scalatest.FunSuite

trait LsTestingBase extends FunSuite with SharedSparkContext with DataFrameSuiteBase {

  def loadTestResourcesCSV(relativePath: String, schema: StructType = null): DataFrame = {
    val csvReader: DataFrameReader = spark.read.option("header", "true").option("delimiter", ";")
    val readerWithSchema: DataFrameReader = if(schema == null) csvReader else csvReader.schema(schema)
    readerWithSchema.csv(getClass.getResource(relativePath).getFile)
  }

  def setNullableToFalse(columns: Seq[String]): DataFrame => DataFrame = df => columns.foldLeft(df){
    (acc, column) => setNullableStateOfColumn(acc, column)
  }

  /**
    * Set nullable property of column.
    * @param dataFrame source DataFrame
    * @param columnName is the column name to change
    * @param nullable is the flag to set, such that the column is  either nullable or not
    */
  def setNullableStateOfColumn(dataFrame: DataFrame, columnName: String, nullable: Boolean = false) : DataFrame = {

    // get schema
    val schema = dataFrame.schema
    // modify [[StructField] with name `columName`
    val newSchema = StructType(schema.map {
      case StructField( name, dataType, _, metadata) if name.equals(columnName) => StructField( name, dataType, nullable = nullable, metadata)
      case y: StructField => y
    })
    // apply new schema
    dataFrame.sqlContext.createDataFrame( dataFrame.rdd, newSchema )
  }

}
