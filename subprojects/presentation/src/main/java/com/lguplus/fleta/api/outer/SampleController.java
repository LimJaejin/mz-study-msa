// package com.lguplus.fleta.api.outer;
//
// import com.lguplus.fleta.data.vo.sample.SampleParamsVo;
// import com.lguplus.fleta.data.vo.sample.SampleQueryParamsVo;
// import com.lguplus.fleta.exception.ParameterValidateException;
// import com.lguplus.fleta.service.SampleService;
// import io.swagger.annotations.Api;
// import io.swagger.annotations.ApiOperation;
// import io.swagger.annotations.ApiResponse;
// import io.swagger.annotations.ApiResponses;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.http.ResponseEntity;
// import org.springframework.validation.BindingResult;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
//
// import javax.validation.Valid;
//
// @Api(tags="샘플 API")
// @Slf4j
// @RequiredArgsConstructor
// @RequestMapping(value = "/sample")
// @RestController
// public class SampleController {
//
//     private final SampleService sampleService;
//
//     @ApiOperation(value="샘플 멤버 생성", notes = "가입 여부 확인 후 샘플 멤버를 등록한다.")
//     @ApiResponses({
//             @ApiResponse(code = 200, message = "성공 시 응답 메시지 문자열 반환", response = Object.class)
//     })
//     @PostMapping(value = "")
//     public ResponseEntity<String> create(@Valid SampleParamsVo param, BindingResult bindingResult) {
//         // API마다 '|'의 수는 다르므로 요청 파라미터 관련 오류는 공통 ExceptionHandler가 아닌 controller에서 처리한다.
//         if (bindingResult.hasErrors()) {
//             throw new ParameterValidateException(bindingResult.getAllErrors(), "1|REQUEST PARAMETER ERROR|0||||||\f");
//         }
//
//         return ResponseEntity.ok(this.sampleService.create(param.toDto(), param.toMappDto()));
//     }
//
//     @ApiOperation(value="샘플 멤버 목록 조회", notes = "샘플 멤버 & 샘플 멤버 매핑 목록을 조회한다.")
//     @ApiResponses({
//             @ApiResponse(code = 200, message = "성공 시 응답 메시지 문자열 반환", response = Object.class)
//     })
//     @GetMapping(value = "")
//     public ResponseEntity<String> getSamples(SampleQueryParamsVo param) {
//         return ResponseEntity.ok(this.sampleService.getSamples(param.toDto()));
//     }
//
//     @ApiOperation(value="배드 쿼리 테스트 - 오류 throw", notes = "오류를 throw하는 배드 쿼리를 테스트한다.")
//     @ApiResponses({
//             @ApiResponse(code = 200, message = "성공 시 응답 메시지 문자열 반환", response = Object.class)
//     })
//     @GetMapping(value = "/bad-query/throw-ex")
//     public ResponseEntity<String> badQueryThrowEx(SampleQueryParamsVo param) {
//         return ResponseEntity.ok(this.sampleService.badQueryThrowEx(param.toDto()));
//     }
//
//     @ApiOperation(value="배드 쿼리 테스트 - 오류 catch", notes = "오류를 catch하는 배드 쿼리를 테스트한다.")
//     @ApiResponses({
//             @ApiResponse(code = 200, message = "성공 시 응답 메시지 문자열 반환", response = Object.class)
//     })
//     @GetMapping(value = "/bad-query/catch-ex")
//     public ResponseEntity<String> badQueryCatchEx(SampleQueryParamsVo param) {
//         return ResponseEntity.ok(this.sampleService.badQueryCatchEx(param.toDto()));
//     }
//
//     @ApiOperation(value="sql 문법 오류가 발생하는 쿼리와 정상 쿼리를 함께 수행", notes = "sql 문법 오류가 발생하는 쿼리와 정상 쿼리를 함께 수행하는 상황에서 하나라도 성공하면 성공 결과를 응답한다.")
//     @ApiResponses({
//             @ApiResponse(code = 200, message = "성공 시 응답 메시지 문자열 반환", response = Object.class)
//     })
//     @GetMapping(value = "/complex-ex")
//     public ResponseEntity<String> complexEx(SampleQueryParamsVo param) {
//         return ResponseEntity.ok(this.sampleService.complexEx(param.toDto()));
//     }
//
//     @ApiOperation(value="샘플 멤버 전체 조회 - entity를 직접 set하면 안되는 이유", notes = "샘플 멤버 전체 조회 - entity를 직접 set하면 안되는 이유를 테스트한다.")
//     @ApiResponses({
//             @ApiResponse(code = 200, message = "성공 시 응답 메시지 문자열 반환", response = Object.class)
//     })
//     @GetMapping(value = "/members")
//     public ResponseEntity<String> getAllMembers() {
//         return ResponseEntity.ok(this.sampleService.getAllMembers());
//     }
//
// }
