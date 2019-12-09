package com.samsolutions.recipes.service;

import org.modelmapper.ModelMapper;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Service to map Entity -> DTO.
 *
 * @author kaminskiy.alexey
 * @since 2019.10
 */

public interface ModelMapperService {
    /*
    static Field[] add(Field[] arr, Field[] elements) {
        Field[] tempArr = new Field[arr.length + elements.length];
        System.arraycopy(arr, 0, tempArr, 0, arr.length);

        for (int i = 0; i < elements.length; i++)
            tempArr[arr.length + i] = elements[i];
        return tempArr;
    }

     */

    default void map(Object src, Object dest) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(src, dest);
    }

    default void map(List src, List dest) {
        Object dto = dest.get(0);
        Class dtoClass = dto.getClass();
        for (int i = 0; i < src.size(); i++) {
            try {
                if (i != 0) {
                    Object newInstance = dtoClass.newInstance();
                    dest.add(newInstance);
                }
                mapCustom(src.get(i), dest.get(i));
            } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }

    default void mapCustom(Object src, Object dest) throws IllegalAccessException, NoSuchFieldException {
        Class srcClass = src.getClass();
        Class superClass = srcClass.getSuperclass();
        Class destClass = dest.getClass();
        Field[] classFields = srcClass.getDeclaredFields();
        Field[] superClassField = superClass.getDeclaredFields();
        int classFieldsLen = classFields.length;
        int superClassFieldLen = superClassField.length;
        Field[] fields = new Field[classFieldsLen + superClassFieldLen];

        System.arraycopy(classFields, 0, fields, 0, classFieldsLen);
        System.arraycopy(superClassField, 0, fields, classFieldsLen, superClassFieldLen);

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
