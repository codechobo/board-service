package com.example.boardservice.module.post.web.dto.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class RequestPostReservationSaveDtoTest {

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("")
    void RequestPostReservationSaveDtoTest() throws JsonProcessingException {
        RequestPostReservationSaveDto dto = RequestPostReservationSaveDto.builder()
                .author("작성자")
                .title("제목")
                .content("글 내용")
                .reservationDateTime(LocalDateTime.now())
                .build();

        String s = objectMapper.writeValueAsString(dto);

        System.out.println(s);
    }

}