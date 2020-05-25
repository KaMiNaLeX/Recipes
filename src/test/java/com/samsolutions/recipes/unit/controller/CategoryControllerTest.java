package com.samsolutions.recipes.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samsolutions.recipes.BaseTest;
import com.samsolutions.recipes.controller.CategoryRestController;
import com.samsolutions.recipes.dto.CategoryDTO;
import com.samsolutions.recipes.service.CategoryService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.UUID;

import static org.mockito.Mockito.when;

public class CategoryControllerTest extends BaseTest {

    @LocalServerPort
    int randomServerPort;
    @MockBean
    private CategoryRestController categoryRestController;
    @MockBean
    private CategoryService categoryService;
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryRestController)
                .build();
    }

    @Test
    public void testGetCategoriesListSuccess() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/api/category/?page=0&size=10&sort=name";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertFalse((Objects.requireNonNull(result.getBody())).isEmpty());
    }

    @Test
    public void addCategory() throws Exception {
        CategoryDTO categoryDTO = createCategoryDTO();
        when(categoryService.createCategory(categoryDTO)).thenReturn(categoryDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/category/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(categoryDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void updateCategory() throws Exception {
        CategoryDTO categoryDTO = createCategoryDTO();
        categoryService.createCategory(categoryDTO);
        when(categoryService.updateCategory(categoryDTO.getId(), categoryDTO)).thenReturn(categoryDTO);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/category/update/" + categoryDTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(categoryDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteCategory() throws Exception {
        CategoryDTO categoryDTO = createCategoryDTO();
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/category/delete/" + categoryDTO.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private CategoryDTO createCategoryDTO() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setImgSource(null);
        categoryDTO.setTag("test");
        categoryDTO.setDescription("test");
        categoryDTO.setName("test");
        categoryDTO.setId(UUID.randomUUID());
        return categoryDTO;
    }
}
