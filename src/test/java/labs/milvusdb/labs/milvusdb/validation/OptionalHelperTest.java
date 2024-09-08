package labs.milvusdb.labs.milvusdb.validation;

import labs.milvusdb.exception.MissingParameterException;
import labs.milvusdb.service.valueobject.AddDocumentVO;
import labs.milvusdb.validation.OptionalHelper;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OptionalHelperTest {

    @Test
    void testGetIfNotNullWithNonNullString() {
        String value = "Hello";

        Optional<String> result = OptionalHelper.getIfNotNull(value);

        assertTrue(result.isPresent());
        assertEquals("Hello", result.get());
    }

    @Test
    void testGetIfNotNullWithEmptyString() {
        String value = "";

        Optional<String> result = OptionalHelper.getIfNotNull(value);

        assertFalse(result.isPresent());
    }

    @Test
    void testGetIfNotNullWithBlankString() {
        String value = "   ";

        Optional<String> result = OptionalHelper.getIfNotNull(value);

        assertFalse(result.isPresent());
    }

    @Test
    void testGetIfNotNullWithNullValue() {
        Object value = null;

        Optional<Object> result = OptionalHelper.getIfNotNull(value);

        assertFalse(result.isPresent());
    }

    @Test
    void testGetIfNotNullWithNonNullObject() {
        Object value = new Object();

        Optional<Object> result = OptionalHelper.getIfNotNull(value);

        assertTrue(result.isPresent());
        assertEquals(value, result.get());
    }

    @Test
    void testGetMapWithNonEmptyMap() {
        Map<String, String> map = Map.of("key1", "value1", "key2", "value2");

        Optional<Map<String, String>> result = OptionalHelper.getMap(map);

        assertTrue(result.isPresent());
        assertEquals(map, result.get());
    }

    @Test
    void testGetMapWithEmptyMap() {
        Map<String, String> emptyMap = Map.of();

        Optional<Map<String, String>> result = OptionalHelper.getMap(emptyMap);

        assertFalse(result.isPresent());
    }

    @Test
    void testGetMapWithEmptyKeyAndValue() {
        Map<String, String> invalidMap = Map.of("", "", "key2", "value2");

        Optional<Map<String, String>> result = OptionalHelper.getMap(invalidMap);

        assertFalse(result.isPresent());
    }

    @Test
    void testDocumentVO() {
        Map<String, Object> invalidMap = Map.of("", "", "key2", "value2");

        assertThrows(MissingParameterException.class, () -> new AddDocumentVO(null));
        assertThrows(MissingParameterException.class, () -> new AddDocumentVO(""));
        assertThrows(MissingParameterException.class, () -> new AddDocumentVO(" "));

        assertThrows(MissingParameterException.class, () -> new AddDocumentVO(" T ", null));
        assertThrows(MissingParameterException.class, () -> new AddDocumentVO(" T ", Map.of()));
        assertThrows(MissingParameterException.class, () -> new AddDocumentVO(" T ", invalidMap));

    }
}
