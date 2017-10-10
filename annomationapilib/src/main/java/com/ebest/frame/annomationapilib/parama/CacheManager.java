package com.ebest.frame.annomationapilib.parama;


import com.ebest.frame.annomationlib.parama.annomation.BundleConverter;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * 缓存管理器。用于对一些类进行缓存处理。达到运行加速的效果
 */
public final class CacheManager {

    /* 数据转换器容器: 存储所有使用过的有效的转换器实例。避免多次进行创建*/
    private final static Map<Class, BundleConverter> CONVERTER_CONTAINERS = new HashMap<>();
    /* 实体类中字段数据类型容器：避免每次进行注入的时候都去反射读取*/
    private final static Map<Class, Map<String, Type>> TYPES = new HashMap<>();

    static BundleConverter transformConverter(Class<? extends BundleConverter> converterClass) {
        if (converterClass == null) {
            return null;
        }
        BundleConverter converter = CONVERTER_CONTAINERS.get(converterClass);
        if (converter == null) {
            try {
                converter = converterClass.newInstance();
                CONVERTER_CONTAINERS.put(converterClass, converter);
            } catch (Throwable e) {
                throw new RuntimeException(String.format("The subclass of BundleConverter %s should provided an empty construct method.", converterClass), e);
            }
        }
        return converter;
    }

    /**
     * 获取数据实体类entity中指定字段名fieldName的字段的数据类型。
     * @param fieldName 字段名
     * @param entity 实体类
     * @return 反射获取到的真实的数据类型。
     */
    public static Type findType(String fieldName, Class entity) {
        Map<String, Type> fieldsMap = TYPES.get(entity);
        if (fieldsMap == null) {
            fieldsMap = new HashMap<>();
            Field[] fields = entity.getDeclaredFields();
            for (Field field : fields) {
                fieldsMap.put(field.getName(), field.getGenericType());
            }
            TYPES.put(entity, fieldsMap);
        }
        return fieldsMap.get(fieldName);
    }
}
