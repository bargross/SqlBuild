package util.mapper;

import exception.StreamAlreadyConsumedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

class MapperTest {

    @Test
    void testMap() {
        String[] result = Mapper.map(new String[]{new String()}, null);
        Assertions.assertArrayEquals(new String[]{new String()}, result);
    }

    @Test
    void testMap2() {
        List<String> result = Mapper.map(List.of(new String()), null);
        Assertions.assertEquals(List.of(new String()), result);
    }

    @Test
    void testMapToArray() {
        String[] result = Mapper.mapToArray(List.of(new String()), null);
        Assertions.assertArrayEquals(new String[]{new String()}, result);
    }

    @Test
    void testFilter() {
        String[] result = Mapper.filter(new String[]{new String()}, null);
        Assertions.assertArrayEquals(new String[]{new String()}, result);
    }

    @Test
    void testToList() {
        List<String> result = Mapper.toList(new String[]{new String()});
        Assertions.assertEquals(List.of(new String()), result);
    }

    @Test
    void testToList2() throws StreamAlreadyConsumedException {
        List<String> result = Mapper.toList((Stream<String>) null);
        Assertions.assertEquals(List.of(new String()), result);
    }

    @Test
    void testToCharArray() {
        char[] result = Mapper.toCharArray("value");
        Assertions.assertArrayEquals(new char[]{'a'}, result);
    }

    @Test
    void testToCharacterArray() {
        Character[] result = Mapper.toCharacterArray("value");
        Assertions.assertArrayEquals(new Character[]{Character.valueOf('a')}, result);
    }

    @Test
    void testToArray() {
        String[] result = Mapper.toArray(List.of(new String()));
        Assertions.assertArrayEquals(new String[]{new String()}, result);
    }
}