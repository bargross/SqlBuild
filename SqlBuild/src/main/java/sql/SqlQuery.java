package sql;

import query.build.complex.QueryComplexBuilder;
import query.build.complex.IQueryComplexBuilder;
import query.build.simple.QuerySimpleBuilder;
import query.build.simple.IQuerySimpleBuilder;
import query.expression.IQueryFieldExpressionBuilder;

import java.util.function.Consumer;

public final class SqlQuery {

    private SqlQuery() {} // static factory only

    // Select factory methods
    public static IQuerySimpleBuilder select(Consumer<IQueryFieldExpressionBuilder> predicate) {
        return new QuerySimpleBuilder().select(predicate);
    }

    public static IQuerySimpleBuilder selectAll() {
        return new QuerySimpleBuilder().selectAll();
    }

    public static IQueryComplexBuilder selectDistinct(Consumer<IQueryFieldExpressionBuilder> fieldBuilderPredicate) {
        return new QueryComplexBuilder().selectDistinct(fieldBuilderPredicate);
    }

    public static IQueryComplexBuilder selectAllDistinct() {
        return new QueryComplexBuilder().selectAllDistinct();
    }

//    // Insert factory
//    public static InsertBuilder insertInto(String table) {
//        return new InsertBuilder().insertInto(table);
//    }
//
//    // Update factory
//    public static UpdateBuilder update(String table) {
//        return new UpdateBuilder().table(table);
//    }
//
//    // Delete factory
//    public static DeleteBuilder deleteFrom(String table) {
//        return new DeleteBuilder().from(table);
//    }
//
//    // Subquery helper (for your FieldDefinitionBuilder)
//    public static FieldDefinitionBuilder subQuery(QueryBuilder subQuery) {
//        return new FieldDefinitionBuilder(subQuery);
//    }
}