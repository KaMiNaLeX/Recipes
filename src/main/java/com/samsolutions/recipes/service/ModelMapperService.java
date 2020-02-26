package com.samsolutions.recipes.service;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Service to map Entity -> DTO.
 *
 * @author kaminskiy.alexey
 * @since 2019.10
 */
public class ModelMapperService {

    public static void map(Object src, Object dest) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(src, dest);
    }

    public static <T, V> List<V> mapListLambda(List<T> src, Class<V> dtoClass) {
        return src
                .parallelStream()
                .collect(ArrayList::new,
                        (result, item) -> {
                            V destItem = null;
                            try {
                                destItem = dtoClass.newInstance();
                            } catch (InstantiationException | IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            map(item, destItem);
                            result.add(destItem);
                        }, ArrayList::addAll);
    }

    public static void mapList(List src, List dest) {
        Object dto = dest.get(0);
        Class dtoClass = dto.getClass();
        for (int i = 0; i < src.size(); i++) {
            try {
                if (i != 0) {
                    Object newInstance = dtoClass.newInstance();
                    dest.add(newInstance);
                }
                map(src.get(i), dest.get(i));
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}
