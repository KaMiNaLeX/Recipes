package com.samsolutions.recipes.service;

import com.samsolutions.recipes.service.helper.DbFieldsParser;
import org.modelmapper.ModelMapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * Service to map Entity -> DTO.
 *
 * @author kaminskiy.alexey
 * @since 2019.10
 */

public interface ModelMapperService {
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
            } catch (InstantiationException | IllegalAccessException e) {
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
    default void mapListMapToDto(List<Map<String, Object>> src, List dest, Class dtoClass)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        for (int i = 0; i < src.size(); i++) {
            Map<String, Object> map = src.get(i);
            Object objectDto = dtoClass.getConstructor().newInstance();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String parsedField = DbFieldsParser.getDtoFieldFromDb(entry.getKey());
                String dtoField = parsedField == null ? entry.getKey() : parsedField;
                Field field = null;
                try {
                    field = dtoClass.getDeclaredField(dtoField);
                } catch (NoSuchFieldException e) {
                    continue;
                }
                field.setAccessible(true);
                field.set(objectDto, entry.getValue());
            }
            dest.add(objectDto);
        }
    }

}
