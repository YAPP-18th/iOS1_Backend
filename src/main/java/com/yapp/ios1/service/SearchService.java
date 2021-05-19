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

    public List<MyBookSearchDto> searchMyBook(Long userId) {
        return searchMapper.searchMyBook(userId);
    }

    public List<UserSearchDto> searchUser(Long userId) {
        return searchMapper.searchUser(userId);
    }

    public List<BookMarkSearchDto> searchBookMark(Long userId) {
        searchMapper.searchBookMark(userId);
        return null;
    }
}
