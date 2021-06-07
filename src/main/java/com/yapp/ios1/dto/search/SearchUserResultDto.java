package com.yapp.ios1.dto.search;

import com.yapp.ios1.model.user.Friend;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * created by jg 2021/05/20
 * 분리하기
 */
@AllArgsConstructor
@Getter
public class SearchUserResultDto {
    private List<Friend> searchs;
}
