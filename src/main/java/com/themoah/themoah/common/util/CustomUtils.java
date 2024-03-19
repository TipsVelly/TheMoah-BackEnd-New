package com.themoah.themoah.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CustomUtils {
    public static List<?> convertObjectToList(Object obj) {
    List<?> list = new ArrayList<>();
        if(obj.getClass().isArray()) {
            list = Arrays.asList((Object[]) obj);
            
        } else if(obj instanceof Collection)  {
            list = new ArrayList<>((Collection<?>) obj);
        } else {
            list = null;
        }
        return list;
    }

    public static boolean isCollection(Object obj) {
        return obj.getClass().isArray() || obj instanceof Collection;
    }
}
