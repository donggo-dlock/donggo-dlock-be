package com.example.base.medium;

import com.example.base.web.controller.HealthCheckController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = HealthCheckController.class)
@ActiveProfiles("local")
class HealthCheckControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void 서버는_리턴값이_void_타입이면_201_상태이다() throws Exception {
        //when
        //then
        mockMvc.perform(get("/health-check"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.path").value("/health-check"))
                .andExpect(jsonPath("$.code").value("0001"))
                .andExpect(jsonPath("$.message").value("생성됨"))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}
