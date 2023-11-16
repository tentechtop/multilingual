package com.alphay.boot.official.aspect;

public class RedisKeyHolder {
    private static final ThreadLocal<String> modifiedKeyHolder = new ThreadLocal<>();

    public static void setModifiedKey(String modifiedKey) {
        modifiedKeyHolder.set(modifiedKey);
    }

    public static String getModifiedKey() {
        return modifiedKeyHolder.get();
    }

    public static void clear() {
        modifiedKeyHolder.remove();
    }
}
