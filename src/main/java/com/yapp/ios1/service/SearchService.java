package com.yapp.ios1.service;

import com.yapp.ios1.dto.search.*;
import com.yapp.ios1.mapper.SearchMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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

    public List<UserSearchDto> searchUser(String keyword, Long userId) {
        List<UserSearchDto> friendUsers = searchMapper.searchUser(keyword, userId, FRIEND.getFriendStatus());
        List<UserSearchDto> requestFriendUsers = searchMapper.searchUser(keyword, userId, REQUEST.getFriendStatus());
        List<UserSearchDto> noFriendUsers = searchMapper.searchNoFriends(keyword, userId);
        return searchResult(friendUsers, requestFriendUsers, noFriendUsers);
    }

    // 클라이언트에게 데이터 형식을 깔끔하게 주기 위한 처리 메소드 (머지 전 주석 삭졔)
    private List<UserSearchDto> searchResult(List<UserSearchDto> friendUsers,
                                             List<UserSearchDto> requestFriendUsers,
                                             List<UserSearchDto> noFriendUsers) {
        List<UserSearchDto> list = new ArrayList<>();
        list.addAll(friendUsers);
        list.addAll(requestFriendUsers);
        list.addAll(noFriendUsers);
        return list;
    }

    public List<BookMarkSearchDto> searchBookMark(String keyword, Long userId) {
        return searchMapper.searchBookMark(keyword, userId);
    }
}
