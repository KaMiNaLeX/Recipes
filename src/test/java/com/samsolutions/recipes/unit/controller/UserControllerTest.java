package com.samsolutions.recipes.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samsolutions.recipes.BaseTest;
import com.samsolutions.recipes.controller.UserRestController;
import com.samsolutions.recipes.dto.UserDTO;
import com.samsolutions.recipes.service.UserService;
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

import static org.mockito.Mockito.when;

public class UserControllerTest extends BaseTest {

    @LocalServerPort
    int randomServerPort;

    @MockBean
    private UserRestController userRestController;
    @MockBean
    private UserService userService;
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userRestController)
                .build();
    }

    @Test
    public void addUser() throws Exception {
        UserDTO userDTO = createUserDTO();
        when(userService.createUser(userDTO)).thenReturn(userDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteUser() throws Exception {
        UserDTO userDTO = createUserDTO();
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/delete/" + userDTO.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    public UserDTO createUserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin("test");
        userDTO.setEmail("test");
        userDTO.setPassword("test");
        userDTO.setFirstName("test");
        userDTO.setLastName("test");
        return userDTO;
    }
}
