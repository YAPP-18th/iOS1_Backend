package com.yapp.ios1.service;

import com.yapp.ios1.dto.search.BookMarkSearchDto;
import com.yapp.ios1.dto.search.MyBookSearchDto;
import com.yapp.ios1.dto.search.UserSearchDto;
import com.yapp.ios1.mapper.SearchMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.yapp.ios1.dto.search.FriendStatus.FRIEND;
import static com.yapp.ios1.dto.search.FriendStatus.REQUEST;

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
        List<UserSearchDto> friendUsers = searchMapper.searchUser(keyword, userId);
        List<UserSearchDto> noFriendUsers = searchMapper.searchNoFriends(keyword, userId);
        return searchResult(friendUsers, noFriendUsers);
    }

    private List<UserSearchDto> searchResult(List<UserSearchDto> friendUsers,
                                             List<UserSearchDto> noFriendUsers) {
        List<UserSearchDto> list = new ArrayList<>();
        list.addAll(friendUsers);
        list.addAll(noFriendUsers);
        return list;
    }

    public List<BookMarkSearchDto> searchBookMark(String keyword, Long userId) {
        return searchMapper.searchBookMark(keyword, userId);
    }
}
