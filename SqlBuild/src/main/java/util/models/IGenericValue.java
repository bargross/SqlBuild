package util.models;

import exception.GenericCastException;

public interface IGenericValue {
    <TValue> void setValue(TValue value);
    <TValue> TValue getAndCast() throws GenericCastException;
}
