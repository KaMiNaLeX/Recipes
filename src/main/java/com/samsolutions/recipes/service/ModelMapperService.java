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

}
