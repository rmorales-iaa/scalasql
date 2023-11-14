package scalasql.operations

import scalasql._
import scalasql.query.Expr
import utest._
import utils.ScalaSqlSuite

trait ExprNumericOpsTests extends ScalaSqlSuite {
  def description = "Operations that can be performed on `Expr[T]` when `T` is numeric"
  def tests = Tests {
    test("plus") - checker(query = Expr(6) + Expr(2), sql = "SELECT (? + ?) as res", value = 8)

    test("minus") - checker(query = Expr(6) - Expr(2), sql = "SELECT (? - ?) as res", value = 4)

    test("times") - checker(query = Expr(6) * Expr(2), sql = "SELECT (? * ?) as res", value = 12)

    test("divide") - checker(query = Expr(6) / Expr(2), sql = "SELECT (? / ?) as res", value = 3)

    test("modulo") - checker(query = Expr(6) % Expr(2), sql = "SELECT MOD(?, ?) as res", value = 0)

    test("bitwiseAnd") - checker(
      query = Expr(6) & Expr(2),
      sqls = Seq("SELECT (? & ?) as res", "SELECT BITAND(?, ?) as res"),
      value = 2
    )

    test("bitwiseOr") - checker(
      query = Expr(6) | Expr(3),
      sqls = Seq("SELECT (? | ?) as res", "SELECT BITOR(?, ?) as res"),
      value = 7
    )

    test("between") - checker(
      query = Expr(4).between(Expr(2), Expr(6)),
      sql = "SELECT ? BETWEEN ? AND ? as res",
      value = true
    )

    test("unaryPlus") - checker(query = +Expr(-4), sql = "SELECT +? as res", value = -4)

    test("unaryMinus") -
      checker(query = -Expr(-4), sqls = Seq("SELECT -? as res", "SELECT -(?) as res"), value = 4)

    test("unaryTilde") - checker(
      query = ~Expr(-4),
      sqls = Seq("SELECT ~? as res", "SELECT BITNOT(?) as res"),
      value = 3
    )

    test("abs") - checker(query = Expr(-4).abs, sql = "SELECT ABS(?) as res", value = 4)

    test("mod") - checker(query = Expr(8).mod(Expr(3)), sql = "SELECT MOD(?, ?) as res", value = 2)

    test("ceil") - checker(query = Expr(4.3).ceil, sql = "SELECT CEIL(?) as res", value = 5.0)

    test("floor") - checker(query = Expr(4.7).floor, sql = "SELECT FLOOR(?) as res", value = 4.0)

    test("floor") - checker(query = Expr(4.7).floor, sql = "SELECT FLOOR(?) as res", value = 4.0)

    test("precedence") - checker(
      query = (Expr(2) + Expr(3)) * Expr(4),
      sql = "SELECT ((? + ?) * ?) as res",
      value = 20
    )
  }
}
