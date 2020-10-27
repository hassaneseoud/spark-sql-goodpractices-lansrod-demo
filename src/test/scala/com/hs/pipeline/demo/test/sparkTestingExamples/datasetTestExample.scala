package com.hs.pipeline.demo.test.sparkTestingExamples

import com.holdenkarau.spark.testing.{DatasetSuiteBase, SharedSparkContext}
import org.scalatest.{FunSuite, Matchers}
import org.scalatest.exceptions.TestFailedException

case class Person(name: String, firstName: String)

case class PersonWithEquals (var name: String, var firstName: String) {
  override def equals(obj: Any): Boolean = obj match {
    case personWithEquals: PersonWithEquals => (name.equals(personWithEquals.name) &&
      firstName.equals(personWithEquals.firstName))
    case _ => false
  }
}
// new PersonWithEquals ("name","firstname") should === Person("name","firstname")

class datasetTestExample extends FunSuite

                with DatasetSuiteBase  with Matchers{

  test(" test assertDatasetApproximateEquals ") {
    import sqlContext.implicits._

    val list1 = List(Person("essid", "dhekra"), Person("hamrouni", "nesrine"))
    val list2 = List(Person("essid", "dhekra"), Person("fjouji", "hanane"))

    val groupFille1 = sc.parallelize(list1).toDS()
    val groupFille2 = sc.parallelize(list2).toDS()

   assertThrows[TestFailedException] {
      assertDatasetApproximateEquals(groupFille1, groupFille2, 0.001)
    }
  }

  test(" test assertDatasetEquals ") {
    import sqlContext.implicits._

    val list1 = List(PersonWithEquals("essid", "dhekra")
      , PersonWithEquals("hamrouni", "nesrine"))
    val list2 = List( PersonWithEquals("essid", "dhekra")
      ,  PersonWithEquals("hamrouni", "nesrine"))

    val groupFille1 = sc.parallelize(list1).toDS()
    val groupFille2 = sc.parallelize(list2).toDS()

      assertDatasetEquals(groupFille1, groupFille2)

  }

  test ("equality ") {
    new PersonWithEquals ("name","firstname") should === (Person("name","firstname"))
  }

  test ("equality 2 ") {
    new PersonWithEquals ("name","firstname") should equal (Person("name","firstname"))

    Some(1) should === (1)
    Some(1) should equal (1)
  }


  test ("equality 3 ") {
    Some(1) should === (1)

  }

  test("equality 4") {
//    Some(1) should equal (1)

  }



}