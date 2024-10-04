package com.project.banking.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.banking.utils.constant.APIUrl;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BranchControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static String id;

    @Order(1)
    @Test
    public void testCreateBranch() throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("code", "001");
        request.put("branchName", "Branch 1");
        request.put("region", "Region 1");
        request.put("city", "City 1");

        mockMvc.perform(post(APIUrl.BRANCH_API)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpectAll(status().isCreated())
                .andDo(result -> {
                    String jsonString = result.getResponse().getContentAsString();
                    Map<String, Object> mapResponse = objectMapper.readValue(jsonString, new TypeReference<>() {
                    });

                    Map<String, Object> data = (Map<String, Object>) mapResponse.get("data");
                    assertNull(mapResponse.get("errors"));
                    assertNotNull(data.get("branchId"));
                    assertEquals(request.get("code"), data.get("code"));
                    assertEquals(request.get("branchName"), data.get("branchName"));
                    assertEquals(request.get("region"), data.get("region"));
                    assertEquals(request.get("city"), data.get("city"));

                    id = data.get("branchId").toString();
                });
    }
}
