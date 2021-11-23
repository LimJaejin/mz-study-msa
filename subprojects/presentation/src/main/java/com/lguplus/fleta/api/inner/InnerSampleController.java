package com.lguplus.fleta.api.inner;

import com.lguplus.fleta.data.dto.sample.SampleMemberDomainDto;
import com.lguplus.fleta.data.mapper.SampleMemberMapper;
import com.lguplus.fleta.data.vo.SampleMemberVo;
import com.lguplus.fleta.service.sample.SampleMemberService;
import com.lguplus.fleta.service.sample.message.SampleMessageStreamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/msa-boilerplate")
public class InnerSampleController {

    private final SampleMemberMapper memberMapper;
    private final SampleMemberService memberService;
    private final SampleMessageStreamService messageStreamService;

    @GetMapping("/members/search")
    public String searchMember(
        @ModelAttribute SampleMemberVo memberVo,
        BindingResult bindingResult
    ) {
        log.debug(">>> memberVo : {}", memberVo);
        SampleMemberDomainDto memberDto = memberMapper.toDto(memberVo);
        memberService.searchMemberByNameAndEmail(memberDto);
        SampleMemberVo memberVoResult = memberMapper.toVo(memberDto);
        log.debug(">>> memberVoResult : {}", memberVoResult);
        return "Ok!";
    }

    @PostMapping("/sendMessage")
    public String sendMessage(@RequestBody String message) {
        messageStreamService.sendMessage(message);
        return "Ok!";
    }
}
