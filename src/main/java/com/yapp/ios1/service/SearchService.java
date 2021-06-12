package com.yapp.ios1.service;

import com.yapp.ios1.mapper.SearchMapper;
import com.yapp.ios1.model.bucket.Bucket;
import com.yapp.ios1.model.user.Friend;
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

    public List<Bucket> searchMyBook(String keyword, Long userId) {
        return searchMapper.searchMyBook(keyword, userId);
    }

    public List<Friend> searchUser(String keyword, Long userId) {
        return searchMapper.searchUser(keyword, userId);
    }

    public List<Bucket> searchBookMark(String keyword, Long userId) {
        return searchMapper.searchBookMark(keyword, userId);
    }
}
