package com.yapp.ios1.service;

import com.yapp.ios1.dto.search.*;
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

    private static final boolean FRIEND = true;
    private static final boolean FRIEND_REQUEST = false;

    private final SearchMapper searchMapper;

    public List<MyBookSearchDto> searchMyBook(String keyword, Long userId) {
        return searchMapper.searchMyBook(keyword, userId);
    }

    public SearchUserResultDto searchUser(String keyword, Long userId) {
        List<UserSearchDto> friendUsers = searchMapper.searchUser(keyword, userId, FRIEND);
        List<UserSearchDto> requestFriendUsers = searchMapper.searchUser(keyword, userId, FRIEND_REQUEST);
        List<UserSearchDto> noFriendUsers = searchMapper.searchNoFriends(keyword, userId);
        return new SearchUserResultDto(friendUsers, requestFriendUsers, noFriendUsers);
    }

    public List<BookMarkSearchDto> searchBookMark(String keyword, Long userId) {
        return searchMapper.searchBookMark(keyword, userId);
    }
}
