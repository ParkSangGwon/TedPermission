package com.gun0912.tedpermission.util;

import java.util.List;

public class ObjectUtils {

    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    public static String[] stringListAsStringArray(List<String> strings) {
        return strings.toArray(new String[strings.size()]);
    }
}
