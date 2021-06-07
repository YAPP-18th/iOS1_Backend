package com.yapp.ios1.service;

import com.yapp.ios1.mapper.UserMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;
//
//    @Test
//    @DisplayName("해당 이메일 존재하지 않을 때 리턴값 테스트")
//    public void 이메일_존재X_테스트() throws SQLException {
//        Optional<User> optional = Optional.ofNullable(null);
//        given(userMapper.findByEmail("없는계정@naver.com")).willReturn(optional);
//
//        assertThat(userService.emailCheck("없는계정@naver.com")).isEqualTo(Optional.empty());
//    }
//
//    @Test
//    @DisplayName("해당 이메일 존재할 때 리턴값 테스트")
//    public void 이메일_존재O_테스트() throws SQLException {
//        User user = new User("ayong0310@naver.com", null, "문아영", "test", "test");
//        Optional<User> optional = Optional.of(user);
//        given(userMapper.findByEmail("ayong0310@naver.com")).willReturn(optional);
//
//        Optional<User> retUser = userService.emailCheck("ayong0310@naver.com");
//        assertThat(retUser).isEqualTo(Optional.of(user));
//    }
}
