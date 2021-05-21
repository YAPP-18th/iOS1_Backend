package com.yapp.ios1.dto.search;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * created by jg 2021/05/20
 */
@AllArgsConstructor
@Getter
public class SearchUserResultDto {
    private List<UserSearchDto> searchs;
}
