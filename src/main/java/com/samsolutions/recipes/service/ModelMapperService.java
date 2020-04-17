package com.samsolutions.recipes.service;

import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Service to map Entity -> DTO.
 *
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@Log4j2
public class ModelMapperService {

    public static void map(Object src, Object dest) {
        try {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.map(src, dest);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public static <T, V> List<V> mapListLambda(List<T> src, Class<V> dtoClass) {
        try {
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
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
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
