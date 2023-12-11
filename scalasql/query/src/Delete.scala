package scalasql.query

import scalasql.core.DialectTypeMappers
import scalasql.core.Context
import scalasql.core.{Queryable, SqlStr, Expr}
import scalasql.core.SqlStr.SqlStringSyntax

/**
 * A SQL `DELETE` query
 */
trait Delete[Q] extends Query.ExecuteUpdate[Int] with Returnable[Q]

object Delete {
  class Impl[Q](val expr: Q, filter: Expr[Boolean], val table: TableRef)(
      implicit dialect: DialectTypeMappers
  ) extends Delete[Q] {
    import dialect._

    protected def renderSql(ctx: Context) = new Renderer(table, filter, ctx).render()

    protected def queryConstruct(args: Queryable.ResultSetIterator): Int = args.get(IntType)
  }

  class Renderer(table: TableRef, expr: Expr[Boolean], prevContext: Context) {
    lazy val tableNameStr =
      SqlStr.raw(prevContext.config.tableNameMapper(Table.name(table.value)))
    implicit val implicitCtx = Context.compute(prevContext, Nil, Some(table))

    def render() = sql"DELETE FROM $tableNameStr WHERE $expr"
  }
}
