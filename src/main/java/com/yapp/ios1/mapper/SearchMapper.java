package com.yapp.ios1.mapper;

import com.yapp.ios1.dto.search.MyBookSearchDto;
import com.yapp.ios1.dto.search.UserSearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * created by jg 2021/05/17
 */
@Mapper
public interface SearchMapper {

    List<UserSearchDto> searchUser(Long userId);
    List<MyBookSearchDto> searchMyBook(Long userId);
    void searchBookMark(Long userId);
}
