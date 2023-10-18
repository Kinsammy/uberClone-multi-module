package io.samtech.utils;

import org.springframework.beans.BeanUtils;

public abstract class CopyUtils {

    public static void copyProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target);
    }
}
