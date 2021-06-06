package com.yapp.ios1.mapper;

import com.yapp.ios1.dto.bucket.BucketDto;
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

    List<UserSearchDto> searchUser(@Param("keyword") String keyword, @Param("userId") Long userId);

    List<UserSearchDto> searchNoFriends(@Param("keyword") String keyword, @Param("userId") Long userId);

    List<BucketDto> searchMyBook(@Param("keyword") String keyword, @Param("userId") Long userId);
    List<BucketDto> searchBookMark(@Param("keyword") String keyword, @Param("userId") Long userId);
}
