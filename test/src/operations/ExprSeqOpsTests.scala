package scalasql.operations

import scalasql._
import utest._
import utils.ScalaSqlSuite

/**
 * Tests for all the aggregate operators that we provide by default
 */
trait ExprSeqOpsTests extends ScalaSqlSuite {
  def tests = Tests {
    test("size") - checker(
      query = Purchase.select.size,
      sql = "SELECT COUNT(1) as res FROM purchase purchase0",
      value = 7
    )

    test("sumBy") {
      test("simple") - checker(
        query = Purchase.select.sumBy(_.count),
        sql = "SELECT SUM(purchase0.count) as res FROM purchase purchase0",
        value = 140
      )

      test("some") - checker(
        query = Purchase.select.sumByOpt(_.count),
        sql = "SELECT SUM(purchase0.count) as res FROM purchase purchase0",
        value = Option(140)
      )

      test("none") - checker(
        query = Purchase.select.filter(_ => false).sumByOpt(_.count),
        sql = "SELECT SUM(purchase0.count) as res FROM purchase purchase0 WHERE ?",
        value = Option.empty[Int]
      )
    }

    test("minBy") {
      test("simple") - checker(
        query = Purchase.select.minBy(_.count),
        sql = "SELECT MIN(purchase0.count) as res FROM purchase purchase0",
        value = 3
      )

      test("some") - checker(
        query = Purchase.select.minByOpt(_.count),
        sql = "SELECT MIN(purchase0.count) as res FROM purchase purchase0",
        value = Option(3)
      )

      test("none") - checker(
        query = Purchase.select.filter(_ => false).minByOpt(_.count),
        sql = "SELECT MIN(purchase0.count) as res FROM purchase purchase0 WHERE ?",
        value = Option.empty[Int]
      )
    }

    test("maxBy") {
      test("simple") - checker(
        query = Purchase.select.maxBy(_.count),
        sql = "SELECT MAX(purchase0.count) as res FROM purchase purchase0",
        value = 100
      )

      test("some") - checker(
        query = Purchase.select.maxByOpt(_.count),
        sql = "SELECT MAX(purchase0.count) as res FROM purchase purchase0",
        value = Option(100)
      )

      test("none") - checker(
        query = Purchase.select.filter(_ => false).maxByOpt(_.count),
        sql = "SELECT MAX(purchase0.count) as res FROM purchase purchase0 WHERE ?",
        value = Option.empty[Int]
      )
    }

    test("avgBy") {
      test("simple") - checker(
        query = Purchase.select.avgBy(_.count),
        sql = "SELECT AVG(purchase0.count) as res FROM purchase purchase0",
        value = 20
      )

      test("some") - checker(
        query = Purchase.select.avgByOpt(_.count),
        sql = "SELECT AVG(purchase0.count) as res FROM purchase purchase0",
        value = Option(20)
      )

      test("none") - checker(
        query = Purchase.select.filter(_ => false).avgByOpt(_.count),
        sql = "SELECT AVG(purchase0.count) as res FROM purchase purchase0 WHERE ?",
        value = Option.empty[Int]
      )
    }
  }
}
