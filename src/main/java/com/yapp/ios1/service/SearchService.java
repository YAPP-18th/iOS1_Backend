package com.yapp.ios1.service;

import com.yapp.ios1.dto.search.BookMarkSearchDto;
import com.yapp.ios1.dto.search.MyBookSearchDto;
import com.yapp.ios1.dto.search.UserSearchDto;
import com.yapp.ios1.mapper.SearchMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by jg 2021/05/17
 */
@RequiredArgsConstructor
@Service
public class SearchService {

    private final SearchMapper searchMapper;

    public List<MyBookSearchDto> searchMyBook(String keyword, Long userId) {
        return searchMapper.searchMyBook(keyword, userId);
    }

    public List<UserSearchDto> searchUser(String keyword, Long userId) {
        checkFriendStatus(userId);
        return searchMapper.searchUser(keyword, userId);
    }

    private void checkFriendStatus(Long userId) {
        int friendStatusCount = searchMapper.checkFriendStatus(userId);
    }

    public List<BookMarkSearchDto> searchBookMark(String keyword, Long userId) {
        return searchMapper.searchBookMark(keyword, userId);
    }
}
