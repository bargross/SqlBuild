package update.set;

public class SetExpressionBuilder implements ISetExpressionBuilder {
    private StringBuffer _builderRef;

    public SetExpressionBuilder(StringBuffer builder) {
        _builderRef = builder;
    }

}
