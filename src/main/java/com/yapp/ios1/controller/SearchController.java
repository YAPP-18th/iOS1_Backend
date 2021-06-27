package com.yapp.ios1.controller;

import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.service.SearchService;
import com.yapp.ios1.annotation.Auth;
import com.yapp.ios1.aop.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.yapp.ios1.message.ResponseMessage.NOT_FOUND_SEARCH_TYPE;
import static com.yapp.ios1.message.ResponseMessage.SUCCESS_SEARCH;

/**
 * created by jg 2021/05/17
 */
@Api(tags = "Search")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2")
public class SearchController {

    private final SearchService searchService;

    @ApiOperation("검색, type = my(마이북), user(유저), mark(북마크) 검색, keyword = 검색키워드")
    @Auth
    @GetMapping("/search")
    public ResponseEntity<ResponseDto> search(@RequestParam("type") String type,
                                              @RequestParam("keyword") String keyword) {
        ResponseDto responseDto = ResponseDto.of(HttpStatus.OK, SUCCESS_SEARCH);
        Long userId = UserContext.getCurrentUserId();

        switch (type) {
            case "my":
                responseDto.setData(searchService.searchMyBook(keyword, userId));
                break;
            case "user":
                responseDto.setData(searchService.searchUser(keyword, userId));
                break;
            case "mark":
                responseDto.setData(searchService.searchBookMark(keyword, userId));
                break;
            default:
                return ResponseEntity.ok(ResponseDto.of(HttpStatus.BAD_REQUEST, NOT_FOUND_SEARCH_TYPE));
        }

        return ResponseEntity.ok(responseDto);
    }
}
