package com.lguplus.fleta.api.inner;

import com.lguplus.fleta.service.message.MessageStreamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/msa-boilerplate")
public class InnerSampleController {

    private final MessageStreamService messageStreamService;

    @PostMapping("/sendMessage")
    public String sendMessage(@RequestBody String message) {
        messageStreamService.sendMessage(message);
        return "Ok!";
    }
}
