package com.samsolutions.recipes.controller;

import com.samsolutions.recipes.exception.CustomGlobalExceptionHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kaminskiy Alexey
 * @since 2020.03
 **/
@Log4j2
@Validated
@RestController
@RequestMapping("/api/comments")
public class CommentsRestController extends CustomGlobalExceptionHandler {
}
