package com.yapp.ios1.controller;

import com.yapp.ios1.common.ResponseMessage;
import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.service.SearchService;
import com.yapp.ios1.utils.auth.Auth;
import com.yapp.ios1.utils.auth.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "Search")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2")
public class SearchController {

    private final SearchService searchService;

    /**
     * type = my(마이북), user(유저), mark(북마크) 검색
     * keyword = 검색 키워드
     */
    // TODO 리팩터링
    @ApiOperation(
            value = "마이북, 유저, 북마크 검색",
            notes = "type = my(마이북), user(유저), mark(북마크) 검색, keyword = 검색키워드"
    )
    @Auth
    @GetMapping("/search")
    public ResponseEntity<ResponseDto> search(@RequestParam("type") String type,
                                              @RequestParam("keyword") String keyword) {
        ResponseDto responseDto = ResponseDto.of(HttpStatus.OK, ResponseMessage.SUCCESS_SEARCH);
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
                return ResponseEntity.ok(ResponseDto.of(HttpStatus.BAD_REQUEST, ResponseMessage.NOT_FOUND_SEARCH_TYPE));
        }

        return ResponseEntity.ok(responseDto);
    }
}
