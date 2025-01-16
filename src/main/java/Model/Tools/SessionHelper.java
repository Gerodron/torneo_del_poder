package Model.Tools;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class SessionHelper {
    private static final ConcurrentMap<String, Object> sessionCache = new ConcurrentHashMap<>();

    public static void addSessionAUTH() {
        sessionCache.put("USER_AUTH", Tool.generarIDUnico("USER_AUTH_UG" ));
    }

    public static String getSessionAUTH() {
        return sessionCache.get("USER_AUTH").toString();
    }

    public static void addSessionHelper(String key, Object value) {
        sessionCache.put(key, value);
    }

    public static Object getSessionHelper(String key) {
        return sessionCache.get(key);
    }

    public static void clearCache() {
        sessionCache.clear();
    }
}
