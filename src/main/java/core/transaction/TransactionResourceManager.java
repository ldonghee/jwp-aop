package core.transaction;

import org.springframework.core.NamedThreadLocal;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class TransactionResourceManager {

    private static final ThreadLocal<Map<Object, Object>> resources = new NamedThreadLocal<>("transactional resources");

    private TransactionResourceManager() {
        throw new AssertionError(String.format("%s can not be instanced", getClass()));
    }

    public static void bindResource(Object key, Object value) {
        Map<Object, Object> resource = resources.get();
        if (resource == null) {
            resource = new HashMap<>();
            resources.set(resource);
        }
        resource.put(key, value);
    }

    public static Optional<Object> resource(Object key) {
        return Optional.ofNullable(resources.get())
                .flatMap(resource -> Optional.ofNullable(resource.get(key)));
    }

    public static void unbindResource(Object key) {
        Map<Object, Object> resource = resources.get();
        if (resource == null) {
            return;
        }
        resource.remove(key);
        if (resource.isEmpty()) {
            resources.remove();
        }
    }
}
