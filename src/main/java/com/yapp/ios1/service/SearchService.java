package com.yapp.ios1.service;

import com.yapp.ios1.model.bucket.Bucket;
import com.yapp.ios1.dto.search.UserSearchDto;
import com.yapp.ios1.mapper.SearchMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * created by jg 2021/05/17
 */
@RequiredArgsConstructor
@Service
public class SearchService {

    private final SearchMapper searchMapper;

    public List<Bucket> searchMyBook(String keyword, Long userId) {
        return searchMapper.searchMyBook(keyword, userId);
    }

    public List<UserSearchDto> searchUser(String keyword, Long userId) {
        List<UserSearchDto> users = searchMapper.searchUser(keyword, userId);
        List<UserSearchDto> noFriendUsers = searchMapper.searchNoFriends(keyword, userId);

        return Stream.concat(users.stream(), noFriendUsers.stream())
                .collect(Collectors.toList());
    }

    public List<Bucket> searchBookMark(String keyword, Long userId) {
        return searchMapper.searchBookMark(keyword, userId);
    }
}
