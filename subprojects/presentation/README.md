Presentation Layer
===
## 개요
+ HTTP Request Controller 및 HTTP Request/Response Data 처리
+ Request 파라미터/페이로드 데이터 유효성(Validation) 검사
+ Filter/Interceptor/MessageSource/Swagger 구성 및 Exception 핸들링
+ API URI Path 및 파라미터 변환
+ 로깅, 보안, 인코딩 처리 등
___
## 주요 클래스들의 역할
### Controller
+ API URI를 선언하고 그에 해당하는 application 레이어의 메소드를 호출하며 실행 성공 시 리턴 값 등을 반환합니다.
+ 클라이언트에서 전송한 요청 파라미터에 대한 검증 결과를 처리합니다.
+ 스웨거 어노테이션을 선언하여 API 스펙을 작성합니다.
### Controller 클래스 샘플 코드

```java
package com.lguplus.fleta.api.outer;

import com.lguplus.fleta.data.vo.sample.SampleParamsVo;
import com.lguplus.fleta.data.vo.sample.SampleQueryParamsVo;
import com.lguplus.fleta.exception.ParameterValidateException;
import com.lguplus.fleta.service.SampleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags="샘플 API")
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/sample")
@RestController
public class SampleController {

    private final SampleService sampleService;
    
    private stataic final String PARAMS_ERR_MSG = "1|REQUEST PARAMETER ERROR|0||||||\f";

    @ApiOperation(value="샘플 멤버 생성", notes = "가입 여부 확인 후 샘플 멤버를 등록한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공 시 응답 메시지 문자열 반환", response = Object.class)
    })
    @PostMapping(value = "")
    public ResponseEntity<String> create(@Valid SampleParamsVo param, BindingResult bindingResult) {
        // API마다 '|'의 수는 다르므로 요청 파라미터 관련 오류는 공통 ExceptionHandler가 아닌 controller에서 처리한다.
        if (bindingResult.hasErrors()) {
            throw new ParameterValidateException(bindingResult.getAllErrors(), PARAMS_ERR_MSG);
        }

        return ResponseEntity.ok(this.sampleService.create(param.toDto(), param.toMappDto()));
    }

    @ApiOperation(value="샘플 멤버 목록 조회", notes = "샘플 멤버 & 샘플 멤버 매핑 목록을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공 시 응답 메시지 문자열 반환", response = Object.class)
    })
    @GetMapping(value = "")
    public ResponseEntity<String> getSamples(SampleQueryParamsVo param) {
        return ResponseEntity.ok(this.sampleService.getSamples(param.toDto()));
    }
}
```
___
### ParamsVo
+ 클라이언트에서 전송한 요청 파라미터를 정의하고 검증 어노테이션을 선언합니다.
+ application 레이어에 전송할 Dto로 변환 처리합니다.

```java
package com.lguplus.fleta.data.vo.sample;

import com.lguplus.fleta.data.annotation.AlphabetAndNumberPattern;
import com.lguplus.fleta.data.dto.sample.SampleMemberDto;
import com.lguplus.fleta.data.dto.sample.SampleMemberMappDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class SampleParamsVo implements Serializable {

    @ApiModelProperty(value = "가입자번호", example = "M14090300006", required = true, position = 1)
    @NotBlank(message = "SA_ID 는 빈값을 입력할 수 없습니다")
    @AlphabetAndNumberPattern
    @Length(min = 7, max = 12)
    private String SA_ID;

    @ApiModelProperty(value = "가입자 STB MAC Address", example = "9893.cc25.bc23", required = true, position = 2)
    @NotBlank(message = "STB_MAC 는 빈값을 입력할 수 없습니다")
    @AlphabetAndNumberPattern
    @Length(min = 14, max = 14)
    private String STB_MAC;

    @ApiModelProperty(value = "샘플 멤버 이름", example = "전강욱", position = 3)
    private String NAME;

    @ApiModelProperty(value = "샘플 멤버 이메일", example = "realsnake1975@gmail.com", position = 4)
    private String EMAIL;

    public SampleMemberDto toDto() {
        return new SampleMemberDto(this.NAME, this.EMAIL);
    }

    public SampleMemberMappDto toMappDto() {
        return new SampleMemberMappDto(this.SA_ID, this.STB_MAC);
    }

}
```
___
### ExceptionHandler
+ controller 내부에서 호출한 application 레이어의 메소드가 오류를 throw할 경우 그에 대한 처리를 담당합니다.
```java
package com.lguplus.fleta.advice.exhandler;

import com.lguplus.fleta.exception.ServiceException;
import com.lguplus.fleta.exception.ParameterValidateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice("com.lguplus.fleta.api.outer")
public class OuterControllerAdvice {

    /**
     * 잘못된 파라미터 Validate 발견시 호출
     * @param ex
     * @return
     */
    @ExceptionHandler({ParameterValidateException.class})
    public ResponseEntity<Object> parameterValidateException(final ParameterValidateException ex) {
        // 잘못된 파라미터는 서버 오류 아님. 그래서 badRequest() 사용하면 안됨
        return ResponseEntity.ok().body(ex.getMessage());
    }

    /**
     * 기존 IMCS 는 서버 오류 발생시 공백을 리턴함.
     * 추후 리턴값은 바뀔 수 있음.
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> globalException(final Exception ex) {
        log.error("globalException", ex);
        return ResponseEntity.ok().body("");
    }

    // application 레이어에서 throw한 오류는 controller를 거쳐 여기서 처리된다.
    @ExceptionHandler({ServiceException.class})
    public ResponseEntity<?> handleServiceException(HttpServletRequest req, final ServiceException ex) {
        log.error(req.getRequestURL() + " ServiceException: ", ex);
        return ResponseEntity.ok().body(ex.getResponseDto().serializeErrorMessage());
    }

}
```
___
## 일반적인 presentaion 레이어 개발
+ 가능한 Controller 클래스 하나 당 하나의 application 레이어 service 클래스와 매칭되도록 개발합니다.
+ 요청 파라미터의 validation 처리 외에 if 문 등 사소한 분기 처리라도 application 레이어로 위임합니다.
  + application 레이어는 domain 레이어로 위임하여 처리합니다.
+ 요청 파라미터에 대한 스웨거 처리는 가능한한 ParamsVo에서 처리하도록 합니다.
+ dto에서 스웨거 관련 파라미터 정보를 작성할 수 없는 경우, controller의 메소드 상단에 스웨거 관련 어노테이션을 선언합니다.