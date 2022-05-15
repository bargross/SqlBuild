package query.expression;

public interface IQueryExpressionBuilder {
    String equals(String fieldValue);
    void isEquals(String fieldValue);
    String like(String fieldValue);
    void isLike(String fieldValue);
    String in(String... fieldValues);
    void isIn(String... fieldValues);
}
