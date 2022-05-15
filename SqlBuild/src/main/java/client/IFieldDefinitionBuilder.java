package client;

import client.enums.SQLFunction;
import client.models.FieldDefinition;
import java.util.List;

public interface IFieldDefinitionBuilder {
    FieldDefinitionBuilder setField(String field, SQLFunction function);
    FieldDefinitionBuilder setField(String field);
    List<FieldDefinition> toList();
}
