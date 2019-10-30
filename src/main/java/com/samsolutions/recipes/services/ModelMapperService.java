package com.samsolutions.recipes.services;

import org.modelmapper.ModelMapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;


/**
 * @author kaminskiy.alexey
 * @since 2019.10
 */

public interface ModelMapperService {
    default void map(Object src, Object dest) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(src, dest);
    }

    default void map(List src, List dest) {
        for (int i = 0; i < src.size(); i++) {
            try {
                mapCustom(src.get(i), dest.get(i));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    default void mapCustom(Object src, Object dest) throws IllegalAccessException {
        Class srcClass = src.getClass();
        Class destClass = dest.getClass();
        Field[] fields = srcClass.getDeclaredFields();
        for (Field field : fields) {
            Field destField;
            try {
                destField = destClass.getDeclaredField(field.getName());
            } catch (NoSuchFieldException e) {
                continue;
            }
            field.setAccessible(true);
            destField.setAccessible(true);
            destField.set(dest, field.get(src));
        }
    }

}
