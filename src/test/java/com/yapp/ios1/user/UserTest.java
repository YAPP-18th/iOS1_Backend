package com.yapp.ios1.user;

import com.yapp.ios1.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * created by jg 2021/03/28
 */

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class UserTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @DisplayName("임시로 만든 API 테스트 => 지금은 의미가 없지만 연습겸 한번 짜보았습니다!(지울 예정)")
    @Test
    void test() throws Exception {
        String test = "test";

        mockMvc.perform(get("/api/v2/"))
                .andExpect(status().isOk())
                .andExpect(content().string(test));

    }
}
