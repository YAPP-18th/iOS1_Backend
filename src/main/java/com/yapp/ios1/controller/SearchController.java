package com.yapp.ios1.controller;

import com.yapp.ios1.common.ResponseMessage;
import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.service.SearchService;
import com.yapp.ios1.utils.auth.Auth;
import com.yapp.ios1.utils.auth.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by jg 2021/05/17
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2")
public class SearchController {

    private final SearchService searchService;

    /**
     * search = my(마이북), user(유저), mark(북마크) 검색
     * @return
     */
    @Auth
    @GetMapping("/search")
    public ResponseEntity<ResponseDto> search(@RequestParam("type") String type,
                                              @RequestParam("keyword") String keyword) {
        ResponseEntity.BodyBuilder ok = ResponseEntity.ok();
        Long userId = UserContext.getCurrentUserId();

        switch (type) {
            case "my":
                return ok.body(ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS_SEARCH, searchService.searchMyBook(keyword, userId)));
            case "user":
                return ok.body(ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS_SEARCH,  searchService.searchUser(keyword, userId)));
            case "mark":
                return ok.body(ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS_SEARCH, searchService.searchBookMark(keyword, userId)));
            default:
                return ok.body(ResponseDto.of(HttpStatus.BAD_REQUEST, ResponseMessage.NOT_FOUND_SEARCH_TYPE));
        }
    }
}
