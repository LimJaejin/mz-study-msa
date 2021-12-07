package com.lguplus.fleta.api.inner;

import com.lguplus.fleta.data.dto.sample.SampleCustomMemberDto;
import com.lguplus.fleta.service.sample.SampleMemberService;
import com.lguplus.fleta.service.sample.message.SampleMessageStreamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/msa-boilerplate")
public class SampleInnerController {

    private final SampleMemberService memberService;
    private final SampleMessageStreamService messageStreamService;

    @GetMapping("/init")
    public void init() {
        memberService.initServiceData();
    }

    @GetMapping("/members/{memberId}")
    public SampleCustomMemberDto getMember(
        @PathVariable int memberId
    ) {
        return memberService.getMember(memberId);
    }

    @GetMapping("/members")
    public List<SampleCustomMemberDto> getAllMemberList() {
        return memberService.getAllMemberList();
    }

    @GetMapping("/main-logic")
    public String doMainLogic() {
        return memberService.doMainLogic();
    }

    @PostMapping("/message/send")
    public String sendMessage(@RequestBody String message) {
        messageStreamService.sendMessage(message);
        return "Ok!";
    }
}
