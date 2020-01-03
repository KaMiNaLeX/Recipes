package com.samsolutions.recipes.config.property;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author kaminskiy.alexey
 * @since 2019.12
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "file")
public class FileStorageProperty {
    private String uploadDir;

}
