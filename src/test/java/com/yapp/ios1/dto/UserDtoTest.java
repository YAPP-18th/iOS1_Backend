package com.yapp.ios1.dto;

import com.yapp.ios1.dto.user.SignUpDto;
import com.yapp.ios1.dto.user.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDtoTest {

    @DisplayName("UserDto Builder 테스트")
    @Test
    public void UserBuilderTest() {
        // given
        SignUpDto signUpDto = new SignUpDto("ayong703@gmail.com", "문아영", "test", "test");

        // when
        UserDto userDto = UserDto.of(signUpDto);

        // then
        assertThat(userDto.getEmail()).isEqualTo(signUpDto.getEmail());
        assertThat(userDto.getSocialType()).isEqualTo(null);
        assertThat(userDto.getCreatedDate()).isEqualTo(null);
    }
}
