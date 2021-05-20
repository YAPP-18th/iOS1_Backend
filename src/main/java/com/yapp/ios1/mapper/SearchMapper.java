package com.yapp.ios1.mapper;

import com.yapp.ios1.dto.search.BookMarkSearchDto;
import com.yapp.ios1.dto.search.MyBookSearchDto;
import com.yapp.ios1.dto.search.UserSearchDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * created by jg 2021/05/17
 */
@Mapper
public interface SearchMapper {

    List<UserSearchDto> searchUser(@Param("keyword") String keyword,
                                   @Param("userId") Long userId,
                                   @Param("friendStatus") String friendStatus);

    List<UserSearchDto> searchNoFriends(@Param("keyword") String keyword, @Param("userId") Long userId);

    List<MyBookSearchDto> searchMyBook(@Param("keyword") String keyword, @Param("userId") Long userId);
    List<BookMarkSearchDto> searchBookMark(@Param("keyword") String keyword, @Param("userId") Long userId);
}
