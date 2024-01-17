package com.example.base.medium;

import com.example.base.web.controller.HealthCheckController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = HealthCheckController.class)
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

    @Test
    void 서버는_리턴값이_void_타입이_아니라면_200_상태이다() throws Exception {
        //when
        //then
        mockMvc.perform(get("/health-check/success"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.path").value("/health-check/success"))
                .andExpect(jsonPath("$.code").value("0000"))
                .andExpect(jsonPath("$.message").value("정상"))
                .andExpect(jsonPath("$.data").value("OK"));
    }

    @Test
    void 서버는_예외가_발생할_때_일정한_양식의_응답을_준다() throws Exception {
        //when
        //then
        mockMvc.perform(get("/health-check/exception"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.path").value("/health-check/exception"))
                .andExpect(jsonPath("$.code").value("C400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다. 요청내용을 확인하세요."))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}
