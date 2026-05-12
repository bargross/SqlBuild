import client.enums.SQLFunction;
import exception.EmptyQueryException;
import sql.SqlQuery;
import sql.SQLColumn;

public class Program {
    public void Main(String[] args) throws EmptyQueryException {

        var fields = SQLColumn
            .setColumn("b", SQLFunction.MAX)
                .setColumnAsQuery(builder ->
                        builder.select(columnBuilder ->
                                columnBuilder.setColumn("column1", SQLFunction.AVG))
                                .from("table-c"), "avgColumn")
            .setColumn("c")
            .setColumn("n")
            .toList();

        var parameterizedQuery = SqlQuery
            .select(fieldBuilder -> fieldBuilder.setColumns(fields))
            .from("table-b")
            .join("table-c")
            .on("b", b -> b.equals("25").or(x -> x.equals("c")))
            .where("b")
            .with(x -> x.like("someValue"))
            .build();

        System.out.println(parameterizedQuery.getSqlString());
    }
}
