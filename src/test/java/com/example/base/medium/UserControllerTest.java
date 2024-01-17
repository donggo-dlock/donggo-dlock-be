package com.example.base.medium;

import com.example.base.user.domain.User;
import com.example.base.user.domain.UserCreate;
import com.example.base.user.domain.UserStatus;
import com.example.base.user.service.port.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        userRepository.save(
                User.builder()
                        .email("test@example.com")
                        .id(1L)
                        .nickname("test")
                        .address("서울시 강남구")
                        .certificationCode("1234-1234-1234-1234")
                        .status(UserStatus.ACTIVE)
                        .build()
        );
    }

    @Test
    void 유저는_계정을_생성할_수_있다() throws Exception {
        //given
        UserCreate userCreate = UserCreate.builder()
                .email("create@example.com")
                .nickname("create")
                .address("서울시 강남구")
                .build();

        //when
        //then
        mockMvc.perform(post("/api/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userCreate)))
                .andExpect(status().isCreated());
    }

    @Test
    void 유저는_주소가_포함된_개인정보를_얻을_수_있다() throws Exception {
        //given
        String email = "test@example.com";
        //when
        //then
        mockMvc.perform(get("/api/users/me")
                        .header("EMAIL", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.path").value("/api/users/me"))
                .andExpect(jsonPath("$.code").value("0000"))
                .andExpect(jsonPath("$.message").value("정상"))
                .andExpect(jsonPath("$.data.id").value("1"))
                .andExpect(jsonPath("$.data.email").value(email))
                .andExpect(jsonPath("$.data.nickname").value("test"))
                .andExpect(jsonPath("$.data.address").value("서울시 강남구"));
    }
}
