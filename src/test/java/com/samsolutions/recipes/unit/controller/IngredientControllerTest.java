package com.samsolutions.recipes.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samsolutions.recipes.BaseTest;
import com.samsolutions.recipes.controller.IngredientRestController;
import com.samsolutions.recipes.dto.IngredientDTO;
import com.samsolutions.recipes.model.Enum.Type;
import com.samsolutions.recipes.service.IngredientService;
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

public class IngredientControllerTest extends BaseTest {

    @LocalServerPort
    int randomServerPort;

    @MockBean
    private IngredientRestController ingredientRestController;
    @MockBean
    private IngredientService ingredientService;
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientRestController)
                .build();
    }

    @Test
    public void testGetIngredientListSuccess() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/api/ingredient/";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertFalse((Objects.requireNonNull(result.getBody())).isEmpty());
    }

    @Test
    public void addIngredient() throws Exception {
        IngredientDTO ingredientDTO = createIngredientDTO();
        when(ingredientService.createIngredient(ingredientDTO)).thenReturn(ingredientDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/ingredient/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(ingredientDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void updateIngredient() throws Exception {
        IngredientDTO ingredientDTO = createIngredientDTO();
        ingredientService.createIngredient(ingredientDTO);
        when(ingredientService.updateIngredient(ingredientDTO.getId(), ingredientDTO)).thenReturn(ingredientDTO);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/ingredient/update/" + ingredientDTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(ingredientDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteIngredient() throws Exception {
        IngredientDTO ingredientDTO = createIngredientDTO();
        ;
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/ingredient/delete/" + ingredientDTO.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private IngredientDTO createIngredientDTO() {
        IngredientDTO ingredientDTO = new IngredientDTO();
        ingredientDTO.setCalories(100);
        ingredientDTO.setDescription("test");
        ingredientDTO.setName("test");
        ingredientDTO.setType(Type.ALCOHOL);
        ingredientDTO.setId(UUID.randomUUID());
        return ingredientDTO;
    }
}
