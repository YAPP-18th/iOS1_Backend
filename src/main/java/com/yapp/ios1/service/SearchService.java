package com.yapp.ios1.service;

import com.yapp.ios1.dto.search.*;
import com.yapp.ios1.mapper.SearchMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yapp.ios1.dto.search.FriendStatus.*;

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

    public SearchUserResultDto searchUser(String keyword, Long userId) {
        List<UserSearchDto> friendUsers = searchMapper.searchUser(keyword, userId, FRIEND.getFriendStatus());
        List<UserSearchDto> requestFriendUsers = searchMapper.searchUser(keyword, userId, REQUEST.getFriendStatus());
        List<UserSearchDto> noFriendUsers = searchMapper.searchNoFriends(keyword, userId);
        setFriendStatus(noFriendUsers);
        return new SearchUserResultDto(friendUsers, requestFriendUsers, noFriendUsers);
    }

    private void setFriendStatus(List<UserSearchDto> noFriendUsers) {
        noFriendUsers.forEach(userSearchDto -> {
            userSearchDto.setFriendStatus(NO_FRIENDS.getFriendStatus());
        });
    }

    public List<BookMarkSearchDto> searchBookMark(String keyword, Long userId) {
        return searchMapper.searchBookMark(keyword, userId);
    }
}
