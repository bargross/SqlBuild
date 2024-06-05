
import client.FieldDefinitionBuilder;
import client.enums.SQLFunction;
import query.build.QuerySimpleBuilder;

public class Program {
    public void Main(String[] args) {

        var fields = new FieldDefinitionBuilder()
            .setField("b", SQLFunction.MAX)
            .setField("c")
            .setField("n")
            .toList();

        var query = new QuerySimpleBuilder()
            .select(fieldBuilder -> fieldBuilder.setMany(fields))
            .from("table-b")
            .join("table-c")
            .on("b", b -> b.equals("25"))
            .where("b")
            .with(x -> x.like("someValue"))
            .toString();

        System.out.println(query);
    }
}
