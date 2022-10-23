package com.example.boardservice.module.hashtag.web;

import com.example.boardservice.module.hashtag.service.HashTagService;
import com.example.boardservice.module.hashtag.web.dto.ResponseHashTagListDto;
import com.example.boardservice.module.hashtag.web.dto.ResponseHashTagSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController
@RequiredArgsConstructor
public class HashTagController {

    private final HashTagService hashTagService;

    @PostMapping("/hash-tags")
    public ResponseEntity<ResponseHashTagSaveDto> createHashTag(@Valid @NotEmpty @RequestParam("name") String hashTagName) {
        ResponseHashTagSaveDto responseHashTagSaveDto = hashTagService.saveHashTag(hashTagName);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseHashTagSaveDto);
    }

    @GetMapping("/hash-tags")
    public ResponseEntity<ResponseHashTagListDto> getHashTagList() {
        ResponseHashTagListDto hashTagNameList = hashTagService.findHashTagNameList();
        return ResponseEntity.status(HttpStatus.OK).body(hashTagNameList);
    }

}
