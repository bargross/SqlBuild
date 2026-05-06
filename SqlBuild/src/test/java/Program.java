
import client.fieldDefinition.FieldDefinitionBuilder;
import client.enums.SQLFunction;
import exception.EmptyQueryException;
import query.build.simpleQuery.QuerySimpleBuilder;

public class Program {
    public void Main(String[] args) throws EmptyQueryException {

        var fields = new FieldDefinitionBuilder()
            .setField("b", SQLFunction.MAX)
                .setFieldAsQuery(builder ->
                        builder.select(columnBuilder ->
                                columnBuilder.setColumn("column1", SQLFunction.AVG))
                                .from("table-c"), "avgColumn")
            .setField("c")
            .setField("n")
            .toList();

        var parameterizedQuery = new QuerySimpleBuilder()
            .select(fieldBuilder -> fieldBuilder.setColumns(fields))
            .from("table-b")
            .join("table-c")
            .on("b", b -> b.equals("25").or(x -> x.equals("c")))
            .where("b")
            .with(x -> x.like("someValue"))
            .build();

        System.out.println(parameterizedQuery.get());
    }
}
