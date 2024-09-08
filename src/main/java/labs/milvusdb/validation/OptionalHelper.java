package labs.milvusdb.validation;

import java.util.Map;
import java.util.Optional;

/**
 * Check and return the values
 */
public class OptionalHelper {
    private OptionalHelper() { }

    public static <T> Optional<T> getIfNotNull(T value) {
        var isEmpty = value == null || (value instanceof String && String.valueOf(value).trim().isEmpty());

        if (isEmpty)
            return Optional.empty();

        return Optional.of(value);
    }

    public static <K, V> Optional<Map<K, V>> getMap(Map<K, V> map) {
        if (map == null || map.isEmpty())
            return Optional.empty();

        boolean isMissingKeyOrValue = map.entrySet().stream()
                .anyMatch(entry -> getIfNotNull(entry.getKey()).isEmpty()
                            && getIfNotNull(entry.getValue()).isEmpty());

        return  isMissingKeyOrValue ? Optional.empty() : Optional.of(map);
    }
}
