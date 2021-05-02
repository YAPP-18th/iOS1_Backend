package com.yapp.ios1.dto;

import com.yapp.ios1.dto.user.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * created by ayoung 2021/04/10
 */
public class ResponseDtoTest {

    @DisplayName("응답 테스트")
    @Test
    public void responseTest() {

        //given
        UserDto userDto = new UserDto("ayong703@gmail.com", "GOOGLE", "문아영","test", "test");

        //when
        ResponseDto res1 = ResponseDto.of(HttpStatus.OK, "응답 테스트 메세지1", userDto);
        ResponseDto res2 = ResponseDto.of(HttpStatus.BAD_REQUEST, "응답 테스트 메세지2");

        //then
        assertThat(res1.getStatus()).isEqualTo(200);
        assertThat(res1.getMessage()).isEqualTo("응답 테스트 메세지1");
        assertThat(res1.getData()).isEqualTo(userDto);

        assertThat(res2.getStatus()).isEqualTo(400);
        assertThat(res2.getMessage()).isEqualTo("응답 테스트 메세지2");
        assertThat(res2.getData()).isEqualTo(null);
    }
}
