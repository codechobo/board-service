package com.example.boardservice.runner;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class AppRunner implements ApplicationRunner {
    private final MessageSource messageSource;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(messageSource.getMessage("greeting", new String[]{"java"}, Locale.US));
        System.out.println(messageSource.getMessage("greeting", new String[]{"java"}, Locale.KOREA));
    }
}
