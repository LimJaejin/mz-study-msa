package com.lguplus.fleta.api.inner;

import com.lguplus.fleta.data.dto.response.InnerResponseDto;
import com.lguplus.fleta.data.dto.sample.SampleCustomMemberDto;
import com.lguplus.fleta.data.dto.sample.SampleInnerDto;
import com.lguplus.fleta.message.SampleMessageStreamService;
import com.lguplus.fleta.service.sample.SampleMemberService;
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

    @GetMapping("/outer/test")
    public String getOuterTest() {
        return memberService.getOuterTest();
    }

    @GetMapping("/inner/test")
    public InnerResponseDto<SampleInnerDto> getInnerHttpTest() {
        SampleInnerDto innerDto = SampleInnerDto.builder()
            .name("Inner HTTP Test")
            .email("test@abc.com")
            .build();
        return InnerResponseDto.of(innerDto);
    }

    @PostMapping("/message/send")
    public String sendMessage(@RequestBody String message) {
        return "Ok!";
    }
}
